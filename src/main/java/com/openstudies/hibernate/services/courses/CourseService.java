package com.openstudies.hibernate.services.courses;

import com.openstudies.model.entities.courses.Course;
import com.openstudies.model.entities.courses.Task;

import java.util.List;

public interface CourseService {

    public void create(Course course);

    public Course get(int id);

    public void update(Course course);

    public void delete(Long id);

    public void getCourseByName(String courseName);

    public List<Course> getOwnerCourses(Integer id);

    public Long addCourseTask(int courseId, Task task);

    public List<Task> getCourseTasks(long courseId);

    Task getTask(long taskId);

    void saveTask(Task task);

}
