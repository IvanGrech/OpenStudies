package com.openstudies.hibernate.dao.courses;

import com.openstudies.model.entities.courses.Course;
import com.openstudies.model.entities.courses.Task;

import java.util.List;

public interface CourseDao {
    void create(Course course);

    Course get(int id);

    void update(Course course);

    void delete(Long id);

    Course getCourseByName(String courseName);

    List<Course> getOwnerCourses(Integer id);

    Long addCourseTask(long courseId, Task task);

    List<Task> getCourseTasks(long courseId);

    Task getTask(long taskId);

    void saveTask(Task task);
}
