package com.openstudies.hibernate.services.common;

import com.openstudies.jwt.JwtTokenUtil;
import com.openstudies.model.entities.User;
import com.openstudies.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    @Transactional
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        if (userRepository.findById(user.getId()) != null) {
            userRepository.save(user);
        }
    }

    @Override
    @Transactional
    public void remove(User user) {
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByLogin(String login) {
        try {
            return userRepository.findByLogin(login);
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
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                return null;
            }
        }
        return null;
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public void removeById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getCurrentUser(String authHeader) {
        String ownerLogin = jwtTokenUtil.getUsernameFromToken(authHeader.substring(7));
        User user = userRepository.findByLogin(ownerLogin);
        return user;
    }
}
