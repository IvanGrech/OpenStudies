package com.openstudies.hibernate.services;

import com.openstudies.hibernate.HibernateRoleDao;
import com.openstudies.model.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    HibernateRoleDao dao;

    @Override
    @Transactional
    public void create(Role role) {
        if (role == null) {
            throw new NullPointerException("role was null");
        }
        try {
            dao.create(role);
        } catch (Exception e) {

        }
    }

    @Override
    @Transactional
    public void update(Role role) {
        if (role == null) {
            throw new NullPointerException("role was null");
        }
        try {
            dao.update(role);
        } catch (Exception e) {

        }
    }

    @Override
    @Transactional
    public void remove(Role role) {
        if (role == null) {
            throw new NullPointerException("role was null");
        }
        try {
            dao.remove(role);
        } catch (Exception e) {

        }
    }

    @Override
    @Transactional
    public Role findByName(String name) {
        if (name == null || "".equals(name)) {
            throw new NullPointerException("name was null");
        }
        try {
            return dao.findByName(name);
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                return  null;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public Role findById(Long id) {
        if (id == null) {
            throw new NullPointerException("id was null");
        }
        try {
            return dao.findRoleById(id);
        }catch (Exception e){
            if(e instanceof NoSuchElementException){
                return  null;
            }
        }
        return null;
    }

}