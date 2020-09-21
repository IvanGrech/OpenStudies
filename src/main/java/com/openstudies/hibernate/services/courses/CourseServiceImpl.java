package com.openstudies.hibernate.services.courses;


import com.openstudies.hibernate.dao.courses.CourseDao;
import com.openstudies.model.entities.courses.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl implements  CourseService{
    @Autowired
    private CourseDao courseDao;

    @Override
    @Transactional
    public void create(Course course) {
        courseDao.create(course);
    }

    @Override
    @Transactional
    public void update(Course course) {
        courseDao.update(course);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        courseDao.delete(id);
    }

    @Override
    @Transactional
    public void getCourseByName(String courseName) {
    }

    @Override
    @Transactional
    public List getOwnerCourses(Long id) {
        return courseDao.getOwnerCourses(id);
    }
}
