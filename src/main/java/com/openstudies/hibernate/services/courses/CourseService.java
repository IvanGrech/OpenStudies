package com.openstudies.hibernate.services.courses;

import com.openstudies.model.entities.User;
import com.openstudies.model.entities.courses.Course;
import com.openstudies.model.entities.courses.Task;
import com.openstudies.model.entities.forms.CourseGrades;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseService {

    void create(Course course);

    void delete(Long id);

    Task addCourseTask(Long courseId, Task task);

    List<Task> getCourseTasks(long courseId);

    void deleteTask(Long taskId);

    ResponseEntity<?> subscribeUserToCourse(String courseCode, User user);

    CourseGrades getCourseGradesForUser(Long courseId, Integer userId);

    List<CourseGrades> getAllCourseGradesForUser(Integer userId);

    void unsubscribeUserFromCourse(Long courseId, User user);
}
