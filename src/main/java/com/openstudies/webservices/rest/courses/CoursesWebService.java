package com.openstudies.webservices.rest.courses;

import com.openstudies.hibernate.services.UserService;
import com.openstudies.hibernate.services.courses.CourseService;
import com.openstudies.jwt.JwtTokenUtil;
import com.openstudies.model.entities.User;
import com.openstudies.model.entities.courses.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
public class CoursesWebService {


    @Autowired
    CourseService courseService;
    @Autowired
    UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @RequestMapping(value = "/courses/create", method = RequestMethod.POST)
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> create(@Valid @RequestBody Course course, HttpServletRequest request) {
        String ownerLogin = jwtTokenUtil.getUsernameFromToken(request.getHeader(HttpHeaders.AUTHORIZATION).substring(7));
        User user = userService.findByLogin(ownerLogin);
        course.setOwner(user);
        courseService.create(course);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/courses/{name}", method = RequestMethod.GET)
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> getCourseByName(@PathVariable("name") String courseName) {

        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    @RequestMapping(value = "/courses/owner/{login}", method = RequestMethod.GET)
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> getOwnerCourses(@PathVariable("login") String login, HttpServletRequest request) {
        String ownerLogin = jwtTokenUtil.getUsernameFromToken(request.getHeader(HttpHeaders.AUTHORIZATION).substring(7));
        if (ownerLogin.equals(login)) {
            User user = userService.findByLogin(login);
            List<Course> courseList = courseService.getOwnerCourses(user.getId());
            return new ResponseEntity<>(courseList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }


    @RequestMapping(value = "/courses", method = RequestMethod.PUT)
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> updateCourse(@RequestBody Course course, HttpServletRequest request) {
        courseService.update(course);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    @RequestMapping(value = "/courses/{id}", method = RequestMethod.DELETE)
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> deleteCourse(@PathVariable("id") Long id) {
        courseService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}
