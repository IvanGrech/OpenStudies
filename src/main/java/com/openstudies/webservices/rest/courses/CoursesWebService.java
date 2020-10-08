package com.openstudies.webservices.rest.courses;

import com.openstudies.hibernate.services.common.FileService;
import com.openstudies.hibernate.services.common.UserService;
import com.openstudies.hibernate.services.courses.CourseService;
import com.openstudies.jwt.JwtTokenUtil;
import com.openstudies.model.entities.User;
import com.openstudies.model.entities.courses.Course;
import com.openstudies.model.entities.courses.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class CoursesWebService {


    @Autowired
    CourseService courseService;
    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @RequestMapping(value = "/courses", method = RequestMethod.POST)
    public ResponseEntity<?> create(@Valid @RequestBody Course course, HttpServletRequest request) {
        String ownerLogin = jwtTokenUtil.getUsernameFromToken(request.getHeader(HttpHeaders.AUTHORIZATION).substring(7));
        User user = userService.findByLogin(ownerLogin);
        course.setOwner(user);
        courseService.create(course);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/courses/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> getCourseByName(@PathVariable("name") String courseName) {

        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    @RequestMapping(value = "/courses/owner/{login}", method = RequestMethod.GET)
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
    public ResponseEntity<?> updateCourse(@RequestBody Course course, HttpServletRequest request) {
        courseService.update(course);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    @RequestMapping(value = "/courses/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCourse(@PathVariable("id") Long id) {
        courseService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/courses/{id}/tasks", method = RequestMethod.POST)
    public ResponseEntity<?> createCourseTasks(@PathVariable("id") Integer id, @RequestBody Task task) {
        long taskId = courseService.addCourseTask(id, task);
        return new ResponseEntity<>(taskId, HttpStatus.OK);
    }

    @RequestMapping(value = "/courses/{id}/tasks/{taskId}/file", method = RequestMethod.POST, consumes = {org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> addCourseTaskFile(@PathVariable("id") Integer courseId, @PathVariable("taskId") Long taskId, @RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        try {
            fileService.saveTaskFile(file, taskId);
        } catch (IOException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/courses/{id}/tasks", method = RequestMethod.GET)
    public ResponseEntity<?> getCourseTasks(@PathVariable("id") Integer id) {
        List<Task> taskList = courseService.getCourseTasks(id);
        taskList.stream().forEach(task -> {
            List<String> fileNames = fileService.getTaskFileNames(task.getId());
            task.setFileNames(fileNames);
        });
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

}
