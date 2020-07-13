package com.openstudies.hibernate;


import com.openstudies.model.entities.Role;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class HibernateRoleDaoImpl implements HibernateRoleDao {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Role role) {
        sessionFactory.getCurrentSession().save(role);
    }

    @Override
    public void update(Role role) {
        sessionFactory.getCurrentSession().update(role);
    }
    @Override
    public void remove(Role role) {
        sessionFactory.getCurrentSession().delete(role);
    }

    @Override
    public Role findByName(String name) {
        if (name == null || name.equals("")) {
            throw new NullPointerException("Name must not be null!");
        }
        List list = null;
        Query query = sessionFactory.getCurrentSession().createQuery("FROM ROLE WHERE NAME = :name");
        query.setParameter("name", name);
        list = query.list();
        return (Role)list.iterator().next();

    }

    @Override
    public Role findRoleById(Long id) {

        if (id == null) {
            throw new NullPointerException("Id must not be null!");
        }

        List list = null;
        Query query = sessionFactory.getCurrentSession().createQuery("FROM ROLE WHERE ID = :id");
        query.setParameter("id", id);
        list = query.list();
        return (Role)list.iterator().next();
    }

}
