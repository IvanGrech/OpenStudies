package com.openstudies.hibernate.dao.courses;

import com.openstudies.hibernate.services.UserService;
import com.openstudies.model.entities.User;
import com.openstudies.model.entities.courses.Course;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class CourseDaoImpl implements CourseDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserService userService;

    @Override
    public void create(Course course) {
        sessionFactory.getCurrentSession().save(course);
    }

    @Override
    public Course getCourseByName(String courseName) {
        if (courseName == null || courseName.equals("")) {
            throw new NullPointerException("Courses name was null or empty string");
        }
        Query query = sessionFactory.getCurrentSession().createQuery("FROM COURSE WHERE TAG = :tg");
        query.setParameter("tg", courseName);
        Course course = (Course)query.list().iterator().next();
        return course;
    }

    @Override
    public List<Course> getOwnerCourses(Long id) {
       Query query = sessionFactory.getCurrentSession().createQuery("FROM COURSE WHERE OWNER_ID = :id");
       query.setParameter("id", id);
       return query.list();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
