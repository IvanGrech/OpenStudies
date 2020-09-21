package com.openstudies.hibernate.services.courses;

import com.openstudies.model.entities.courses.Course;

import java.util.List;

public interface CourseService {

    public void create(Course course);
    public void update(Course course);
    public void delete(Long id);
    public void getCourseByName(String courseName);
    public List<Course> getOwnerCourses(Long id);

}
