package com.openstudies.hibernate.services.courses;


import com.openstudies.hibernate.dao.courses.CourseDao;
import com.openstudies.model.entities.courses.Course;
import com.openstudies.model.entities.courses.Task;
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
    public Course get(int id) {
        return courseDao.get(id);
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
    public List getOwnerCourses(Integer id) {
        return courseDao.getOwnerCourses(id);
    }

    @Override
    @Transactional
    public void addCourseTask(int courseId, Task task) {
        courseDao.addCourseTask(courseId, task);
    }
}
