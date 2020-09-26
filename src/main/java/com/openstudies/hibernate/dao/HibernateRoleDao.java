package com.openstudies.hibernate.dao;

import com.openstudies.model.entities.Role;

public interface HibernateRoleDao {

    public void create(Role role);

    public void update(Role role);

    public void remove(Role role);

    public Role findByName(String name);

    public Role findRoleById(Long id);
}
