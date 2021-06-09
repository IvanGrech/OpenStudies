package com.openstudies.webservices.rest;

import com.openstudies.captcha.CaptchaService;
import com.openstudies.hibernate.services.common.RoleService;
import com.openstudies.hibernate.services.common.UserService;
import com.openstudies.jwt.JwtRequest;
import com.openstudies.jwt.JwtResponse;
import com.openstudies.jwt.JwtTokenUtil;
import com.openstudies.model.entities.Role;
import com.openstudies.model.entities.User;
import com.openstudies.model.entities.forms.AddForm;
import com.openstudies.model.entities.forms.EditForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class UserRestWebService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CaptchaService captchaService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtRequest authenticationRequest) throws Exception {
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getLogin());
        User user = null;
        try {
            user = userService.findByLogin(authenticationRequest.getLogin());
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (user.getPassword().equals(authenticationRequest.getPassword())) {
            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> signupUser(
            @RequestBody AddForm form, HttpServletRequest request) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<AddForm>> constraintViolations = validator
                .validate(form);

        String clientIP = request.getRemoteAddr();

        boolean isValidCaptcha = captchaService.isValid(form.getCaptcha(), clientIP);

        Map errors = new HashMap();
        if (!isValidCaptcha) {
            errors.put("captcha", "not valid captcha");
            errors.put("captcha was", form.getCaptcha());
        }
        for (ConstraintViolation<AddForm> violation : constraintViolations) {
            errors.put(violation.getPropertyPath().toString(),
                    violation.getMessage());
        }
        if (userService.findByLogin(form.getLogin()) != null) {
            errors.put("login", "User with this login exists");
        }
        if (userService.findByEmail(form.getEmail()) != null) {
            errors.put("email", "User with this email exists");
        }
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
        }
        Role role = roleService.findByName("user");
        User user = new User();
        user.setLogin(form.getLogin());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setRole(role);
        user.setPassword(form.getPassword());
        try {
            user.setBirthday(Date.valueOf(form.getBirthday()));
        } catch (IllegalArgumentException ex) {

        }

        user.setEmail(form.getEmail());
        userService.create(user);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUser() {
        List<User> list = userService.findAll();
        return new ResponseEntity<List<User>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }


    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> createUser(
            @RequestBody User user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        AddForm addForm = new AddForm();
        addForm.toUser(user);
        Set<ConstraintViolation<AddForm>> constraintViolations = validator
                .validate(addForm);

        Map errors = new HashMap();
        for (ConstraintViolation<AddForm> violation : constraintViolations) {
            errors.put(violation.getPropertyPath().toString(),
                    violation.getMessage());
        }
        if (userService.findByLogin(user.getLogin()) != null) {
            errors.put("login", "User with this login exists");
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.put("email", "User with this email exists");
        }
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
        }
        userService.create(user);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, String>> updateUser(
            @RequestBody User editedUser) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        EditForm editForm = new EditForm();
        editForm.toUser(editedUser);
        Set<ConstraintViolation<EditForm>> constraintViolations = validator
                .validate(editForm);

        Map errors = new HashMap();
        for (ConstraintViolation<EditForm> violation : constraintViolations) {
            errors.put(violation.getPropertyPath().toString(),
                    violation.getMessage());
        }

        User foundUser = userService.findByEmail(editedUser.getEmail());
        if (foundUser != null && !foundUser.getLogin()
                .equals(editedUser.getLogin())) {
            errors.put("email", "User with this email already exists");
        }

        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
        }

        if (editForm.getPassword().trim().isEmpty()) {
            foundUser = userService.findByLogin(editedUser.getLogin());
            editedUser.setPassword(foundUser.getPassword());
        } else {
            editedUser.setPassword(editForm.getPassword());
        }

        userService.update(editedUser);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Integer id) {
        userService.removeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
