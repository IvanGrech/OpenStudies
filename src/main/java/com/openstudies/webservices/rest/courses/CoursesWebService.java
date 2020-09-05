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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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


    @POST
    @RequestMapping(value = "/courses/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> create(@Valid @RequestBody Course course, HttpServletRequest request) {
        String ownerLogin = jwtTokenUtil.getUsernameFromToken(request.getHeader(HttpHeaders.AUTHORIZATION).substring(7));
        User user = userService.findByLogin(ownerLogin);
        course.setOwner(user);
        courseService.create(course);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @GET
    @RequestMapping(value = "/courses/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> getCourseByName(@PathVariable("name") String courseName) {

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GET
    @RequestMapping(value = "/courses/owner/{login}")
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

    @PUT
    @RequestMapping(value = "/courses")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> updateCourse(@RequestBody Course course, HttpServletRequest request) {
        courseService.update(course);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
