package com.openstudies.hibernate.services.common;

import com.openstudies.hibernate.dao.HibernateUserDao;
import com.openstudies.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private HibernateUserDao hibernateUserDao;

    @Override
    @Transactional
    public void create(User user) {
        hibernateUserDao.create(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        hibernateUserDao.update(user);
    }

    @Override
    @Transactional
    public void remove(User user) {
        hibernateUserDao.remove(user);
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return hibernateUserDao.findAll();
    }

    @Override
    public User findByLogin(String login) {
        try {
            return hibernateUserDao.findByLogin(login);
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                return null;
            }
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        try {
            return hibernateUserDao.findByEmail(email);
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                return null;
            }
        }
        return null;
    }

    @Override
    public User findById(Integer id) {
        try {
            return hibernateUserDao.findById(id);
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                return null;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void removeById(Long id) {
        try {
            hibernateUserDao.removeById(id);
        } catch (Exception e) {
        }

    }
}
