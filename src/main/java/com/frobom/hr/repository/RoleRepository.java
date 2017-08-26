package com.frobom.hr.repository;

import com.frobom.hr.entity.Role;

public interface RoleRepository {

    void save(Role role);

    Role findById(int id);

    Role findByName(String name);

    Role getDefaultRole();

    Role getAdminRole();

}
