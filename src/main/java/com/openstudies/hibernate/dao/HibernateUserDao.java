package com.openstudies.hibernate.dao;

import com.openstudies.model.entities.User;

import java.util.List;


public interface HibernateUserDao {

    public void create(User user);

    public void update(User user);

    public void remove(User user);

    public List<User> findAll();

    public User findByLogin(String login);

    public User findByEmail(String email);

    public User findById(Integer id);

    public void removeById(Long id);
}
