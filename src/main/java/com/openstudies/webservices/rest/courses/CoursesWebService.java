package com.openstudies.webservices.rest.courses;

import com.openstudies.hibernate.services.common.FileService;
import com.openstudies.hibernate.services.common.UserService;
import com.openstudies.hibernate.services.courses.CourseService;
import com.openstudies.jwt.JwtTokenUtil;
import com.openstudies.model.entities.User;
import com.openstudies.model.entities.courses.Course;
import com.openstudies.model.entities.courses.Task;
import com.openstudies.model.entities.courses.User2Courses;
import com.openstudies.model.entities.courses.User2Task;
import com.openstudies.model.entities.forms.CourseGrades;
import com.openstudies.model.entities.forms.Grade;
import com.openstudies.model.entities.forms.RestUsersAndTasks;
import com.openstudies.repositories.CourseRepository;
import com.openstudies.repositories.User2CoursesRepository;
import com.openstudies.repositories.User2TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class CoursesWebService {


    @Autowired
    CourseService courseService;
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;

    @Autowired
    User2CoursesRepository user2CoursesRepository;

    @Autowired
    User2TaskRepository user2TaskRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @RequestMapping(value = "/courses", method = RequestMethod.POST)
    public ResponseEntity<?> create(@Valid @RequestBody Course course, HttpServletRequest request) {
        String ownerLogin = jwtTokenUtil.getUsernameFromToken(request.getHeader(HttpHeaders.AUTHORIZATION).substring(7));
        User user = userService.findByLogin(ownerLogin);
        course.setOwner(user);
        course.setCourseCode(String.valueOf(course.hashCode()));
        courseService.create(course);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/courses/owner/{login}", method = RequestMethod.GET)
    public ResponseEntity<?> getOwnerCourses(@PathVariable("login") String login, HttpServletRequest request) {
        String ownerLogin = jwtTokenUtil.getUsernameFromToken(request.getHeader(HttpHeaders.AUTHORIZATION).substring(7));
        if (ownerLogin.equals(login)) {
            User user = userService.findByLogin(login);
            List<Course> courseList = courseRepository.findByOwnerId(user.getId());
            return new ResponseEntity<>(courseList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/courses/subscribed", method = RequestMethod.GET)
    public ResponseEntity<?> getSubscribedCourses(@RequestHeader("Authorization") String authHeader) {
        User currentUser = userService.getCurrentUser(authHeader);
        List<User2Courses> user2CoursesList = user2CoursesRepository.findByUserId(currentUser.getId());
        List<Course> courseList = new LinkedList<>();
        user2CoursesList.forEach((user2Courses) ->
                courseList.add(courseRepository.findById(user2Courses.getUser2CoursesPK().getCourseId()).get())
        );
        return new ResponseEntity<>(courseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/courses/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCourse(@PathVariable("id") Long id) {
        courseService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/courses/{id}/tasks", method = RequestMethod.POST)
    public ResponseEntity<?> createCourseTasks(@PathVariable("id") Long id, @RequestBody Task task) {
        Task createdTask = courseService.addCourseTask(id, task);
        return new ResponseEntity<>(createdTask.getId(), HttpStatus.OK);
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
    public ResponseEntity<?> getCourseTasks(@PathVariable("id") Integer id, @RequestHeader("Authorization") String authHeader) {
        User currentUser = userService.getCurrentUser(authHeader);
        Course course = courseRepository.findById(Long.valueOf(id)).get();
        if(course.getOwner().getId().equals(currentUser.getId())) {
            List<Task> taskList = courseService.getCourseTasks(id);
            taskList.stream().forEach(task -> {
                List<String> fileNames = fileService.getTaskFileNames(task.getId());
                task.setFileNames(fileNames);
            });
            return new ResponseEntity<>(taskList, HttpStatus.OK);
        }
        List<Task> taskList = courseService.getCourseTasks(id);
        taskList.stream().forEach(task -> {
            List<String> fileNames = fileService.getTaskFileNames(task.getId());
            task.setFileNames(fileNames);
            List<String> userFileNames = fileService.getTaskFileNamesForSubscribedUser(task.getId(), currentUser.getId());
            task.setUserFileNames(userFileNames);
        });
        return new ResponseEntity<>(taskList, HttpStatus.OK);

    }


    @RequestMapping(value = "/courses/task/{id}/file/{fileName}/", method = RequestMethod.GET)
    public ResponseEntity<Resource> getTaskFile(@PathVariable("id") Long taskId, @PathVariable("fileName") String fileName) throws FileNotFoundException {
        File file = fileService.getTaskFile(taskId, fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @RequestMapping(value = "/courses/task/{taskId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTask(@PathVariable("taskId") Long taskId) {
        fileService.deleteTaskFiles(taskId);
        courseService.deleteTask(taskId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/courses/subscribe/{courseCode}", method = RequestMethod.PUT)
    public ResponseEntity subscribeToTask(@PathVariable("courseCode") String courseCode, @RequestHeader("Authorization") String authHeader) {
        User currentUser = userService.getCurrentUser(authHeader);
        return courseService.subscribeUserToCourse(courseCode, currentUser);
    }

    @RequestMapping(value = "/courses/saveAnswerFiles/tasks/{taskId}/file", method = RequestMethod.POST, consumes = {org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity saveTaskAnswerFiles(@PathVariable("taskId") Long taskId, @RequestParam(value = "file") MultipartFile file, @RequestHeader("Authorization") String authHeader) {
        User currentUser = userService.getCurrentUser(authHeader);
        try {
            fileService.saveUserAnswerTaskFile(file, taskId, currentUser.getId());
        } catch (IOException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/courses/task/{id}/file/{fileName}/subscribed", method = RequestMethod.GET)
    public ResponseEntity<Resource> getTaskAnswerFile(@PathVariable("id") Long taskId, @PathVariable("fileName") String fileName, @RequestHeader("Authorization") String authHeader) throws FileNotFoundException {
        User currentUser = userService.getCurrentUser(authHeader);
        File file = fileService.getTaskAnswerFile(taskId, currentUser.getId(), fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @RequestMapping(value = "/courses/task/{id}/file/{fileName}/subscribed/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Resource> getTaskAnswerFileForUser(@PathVariable("id") Long taskId, @PathVariable("fileName") String fileName, @PathVariable("userId") Integer userId, @RequestHeader("Authorization") String authHeader) throws FileNotFoundException {
        File file = fileService.getTaskAnswerFile(taskId, userId, fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @RequestMapping(value = "/courses/{courseId}/task/{taskId}/works", method = RequestMethod.GET)
    public ResponseEntity<Resource> getUsersWorksForTasks(@PathVariable("courseId") Long courseId, @PathVariable("taskId") Long taskId) {
        Course course = courseRepository.findById(courseId).get();
        Set<User> subscribedUsers = course.getUsersSubscribed();

        List<RestUsersAndTasks> userTasksList = new LinkedList<>();
        subscribedUsers.forEach((subscribedUser) -> {
            RestUsersAndTasks addedUsersAndTasks = new RestUsersAndTasks();
            addedUsersAndTasks.setUser(subscribedUser);
            List<String> userFileNames = fileService.getTaskFileNamesForSubscribedUser(taskId, subscribedUser.getId());
            addedUsersAndTasks.setFileNames(userFileNames);
            Optional<User2Task> user2Task = user2TaskRepository.findByUserIdAndTaskId(subscribedUser.getId(), taskId);
            if (user2Task.isPresent()) {
                addedUsersAndTasks.setGrade(user2Task.get().getGrade());
            } else {
                addedUsersAndTasks.setGrade(0);
            }
            userTasksList.add(addedUsersAndTasks);
        });

        return new ResponseEntity(userTasksList, HttpStatus.OK);
    }

    @RequestMapping(value = "/courses/task/{taskId}/user/{userId}/grade", method = RequestMethod.PUT)
    public ResponseEntity submitGradeForUserTask(@PathVariable("userId") Integer userId, @PathVariable("taskId") Long taskId, @RequestBody Grade grade) {
        Optional<User2Task> user2Task = user2TaskRepository.findByUserIdAndTaskId(userId, taskId);
        if (user2Task.isPresent()) {
            User2Task task = user2Task.get();
            task.setGrade(grade.getGrade());
            user2TaskRepository.save(task);
        } else {
            User2Task task = new User2Task();
            task.setGrade(grade.getGrade());
            task.setTaskId(taskId);
            task.setUserId(userId);
            user2TaskRepository.save(task);
        }

        return new ResponseEntity(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/courses/{courseId}/user/{userId}/grades", method = RequestMethod.GET)
    public ResponseEntity getCourseGradesForUser(@PathVariable("courseId") Long courseId, @PathVariable("userId") Integer userId) {
        CourseGrades courseGrades = courseService.getCourseGradesForUser(courseId, userId);
        return ResponseEntity.ok(courseGrades);
    }

    @RequestMapping(value = "/courses/{courseId}/grades", method = RequestMethod.GET)
    public ResponseEntity getCourseGradesForCurrentUser(@PathVariable("courseId") Long courseId, @RequestHeader("Authorization") String authHeader) {
        User currentUser = userService.getCurrentUser(authHeader);
        CourseGrades courseGrades = courseService.getCourseGradesForUser(courseId, currentUser.getId());
        return ResponseEntity.ok(courseGrades);
    }

    @RequestMapping(value = "/courses/user/grades", method = RequestMethod.GET)
    public ResponseEntity getAllCourseGradesForUser(@RequestHeader("Authorization") String authHeader) {
        User currentUser = userService.getCurrentUser(authHeader);
        List<CourseGrades> courseGradesList = courseService.getAllCourseGradesForUser(currentUser.getId());
        return ResponseEntity.ok(courseGradesList);
    }
}
