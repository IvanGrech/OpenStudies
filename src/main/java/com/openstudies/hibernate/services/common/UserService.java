package com.openstudies.hibernate.services.common;

import com.openstudies.model.entities.User;

import java.util.List;

public interface UserService {

    void create(User user);

    void update(User user);

    void remove(User user);

    List<User> findAll();

    User findByLogin(String login);

    User findByEmail(String email);

    User findById(Integer id);

    void removeById(Integer id);

    User getCurrentUser(String authHeader);

}
