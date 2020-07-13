package com.openstudies.hibernate.services;

import com.openstudies.model.entities.Role;

public interface RoleService {

    void create(Role role);

    void update(Role role);

    void remove(Role role);

    Role findByName(String name);

    Role findById(Long id);

}
