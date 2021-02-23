package com.openstudies.hibernate.services.common;

import com.openstudies.model.entities.Role;

public interface RoleService {

    void create(Role role);


    Role findByName(String name);

}
