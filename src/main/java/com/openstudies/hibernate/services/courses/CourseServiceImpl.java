package com.openstudies.hibernate.services.courses;


import com.openstudies.model.entities.User;
import com.openstudies.model.entities.courses.Course;
import com.openstudies.model.entities.courses.Task;
import com.openstudies.model.entities.courses.User2Courses;
import com.openstudies.model.entities.courses.User2Task;
import com.openstudies.model.entities.forms.CourseGrades;
import com.openstudies.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    User2TaskRepository user2TaskRepository;

    @Autowired
    User2CoursesRepository user2CoursesRepository;

    @Override
    @Transactional
    public void create(Course course) {
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        List<Task> taskList = taskRepository.findByCourseId(id);
        taskList.forEach((task)->{
            taskRepository.delete(task);
        });
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

    @Override
    public CourseGrades getCourseGradesForUser(Long courseId, Integer userId) {
        CourseGrades courseGrades = new CourseGrades();
        Course course = courseRepository.findById(courseId).get();
        courseGrades.setCourseName(course.getTag());
        List<Task> taskList = taskRepository.findByCourseId(courseId);
        List<Integer> grades = new LinkedList<>();
        List<String> taskNames = new LinkedList<>();
        taskList.forEach((task) -> {
            Optional<User2Task> user2TaskOptional = user2TaskRepository.findByUserIdAndTaskId(userId, task.getId());
            taskNames.add(task.getTag());
            if (user2TaskOptional.isPresent())
                grades.add(user2TaskOptional.get().getGrade());
            else grades.add(0);
        });

        courseGrades.setGradeList(grades);
        courseGrades.setTaskNameList(taskNames);
        return courseGrades;
    }

    @Override
    public List<CourseGrades> getAllCourseGradesForUser(Integer userId) {
        List<User2Courses> user2Courses = user2CoursesRepository.findByUserId(userId);
        List<CourseGrades> courseGradesList = new LinkedList<>();
        user2Courses.forEach((user2Course)->{
            Course course = courseRepository.findById(user2Course.getUser2CoursesPK().getCourseId()).get();
            CourseGrades courseGrades = getCourseGradesForUser(course.getId(), userId);
            courseGradesList.add(courseGrades);
        });
        return courseGradesList;
    }
}
