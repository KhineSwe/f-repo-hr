package com.frobom.hr.service;

import com.frobom.hr.entity.Role;

public interface RoleService {

    // wrap normal repository methods
    void save(Role role);

    Role findById(int id);

    Role findByName(String name);

    Role getDefaultRole();

    Role getAdminRole();

}
