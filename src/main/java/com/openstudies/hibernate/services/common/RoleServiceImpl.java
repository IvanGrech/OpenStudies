package com.openstudies.hibernate.services.common;

import com.openstudies.model.entities.Role;
import com.openstudies.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    @Transactional
    public void create(Role role) {
        if (role == null) {
            throw new NullPointerException("role was null");
        }
        try {
            roleRepository.save(role);
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
            return roleRepository.findByName(name);
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                return  null;
            }
        }
        return null;
    }
}