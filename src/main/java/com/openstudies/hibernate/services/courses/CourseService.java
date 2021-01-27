package com.openstudies.hibernate.services.courses;

import com.openstudies.model.entities.courses.Course;
import com.openstudies.model.entities.courses.Task;

import java.util.List;

public interface CourseService {

    void create(Course course);

    void delete(Long id);

    Task addCourseTask(Long courseId, Task task);

    List<Task> getCourseTasks(long courseId);

    void deleteTask(Long taskId);
}
