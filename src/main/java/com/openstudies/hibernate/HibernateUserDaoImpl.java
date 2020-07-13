package com.openstudies.hibernate;

import com.openstudies.model.entities.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class HibernateUserDaoImpl implements HibernateUserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    public void remove(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }

    @Override
    public List<User> findAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT u FROM USER u");
        List list = null;
        list = query.list();
        return list;
    }

    @Override
    public User findByLogin(String login) {

        if (login == null || login.equals("")) {
            throw new NullPointerException("Login was null or empty string");
        }
        Query query = sessionFactory.getCurrentSession().createQuery("from USER WHERE LOGIN = :lg");
        query.setParameter("lg", login);
       User foundUser=(User) query.list().iterator().next();
        return foundUser;
    }

    @Override
    public User findByEmail(String email) {
        if (email == null || email.equals("")) {
            throw new NullPointerException("Email was null or empty string");
        }

        Query query = sessionFactory.getCurrentSession().createQuery("SELECT u FROM USER u WHERE EMAIL = :em");
        query.setParameter("em", email);
        return (User) query.list().iterator().next();
    }

    @Override
    public User findById(Long id) {
        if (id == null) {
            throw new NullPointerException("Login was null or empty string");
        }

        Query query = sessionFactory.getCurrentSession().createQuery("SELECT u FROM USER u WHERE ID = :i");
        query.setParameter("i", id);
        return (User) query.list().iterator().next();
    }

    @Override
    public void removeById(Long id) {
        if (id == null) {
            throw new NullPointerException("Login was null or empty string");
        }

        Query query = sessionFactory.getCurrentSession().createQuery("DELETE FROM USER WHERE ID = :i");
        query.setParameter("i", id);
        query.executeUpdate();
    }

}
