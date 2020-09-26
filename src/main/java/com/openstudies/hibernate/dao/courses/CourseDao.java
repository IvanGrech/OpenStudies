package com.openstudies.hibernate.dao.courses;

import com.openstudies.model.entities.courses.Course;

import java.util.List;

public interface CourseDao {
    public void create(Course course);
    public void update(Course course);
    public void delete(Long id);
    public Course getCourseByName(String courseName);
    public List<Course> getOwnerCourses(Integer id);
}
