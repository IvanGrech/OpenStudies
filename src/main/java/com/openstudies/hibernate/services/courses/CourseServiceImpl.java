package com.openstudies.hibernate.services.courses;


import com.openstudies.model.entities.User;
import com.openstudies.model.entities.courses.Course;
import com.openstudies.model.entities.courses.Task;
import com.openstudies.repositories.CourseRepository;
import com.openstudies.repositories.TaskRepository;
import com.openstudies.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public void create(Course course) {
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Task addCourseTask(Long courseId, Task task) {
        Course course = new Course();
        course.setId(courseId);
        task.setCourse(course);
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public List<Task> getCourseTasks(long courseId) {
        return taskRepository.findByCourseId(courseId);
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    @Transactional
    public ResponseEntity<?> subscribeUserToCourse(String courseCode, User user) {
        Course course = courseRepository.findByCourseCode(courseCode);
        if (course != null) {
            course.getUsersSubscribed().add(user);
            courseRepository.save(course);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
