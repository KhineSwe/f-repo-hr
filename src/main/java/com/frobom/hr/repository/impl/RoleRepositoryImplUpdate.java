package com.frobom.hr.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.frobom.hr.entity.Role;
import com.frobom.hr.repository.RoleRepository;

@Repository
public class RoleRepositoryImplUpdate implements RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String DEFAULT_ROLE_NAME = "USER";

    private static final String ADMIN_ROLE_NAME = "ADMIN";

    @Override
    public void save(Role role) {
        if (role.getId() == null) {
            this.entityManager.persist(role);
        } else {
            this.entityManager.merge(role);
        }
    }

    @Override
    public Role findById(int id) {
        return this.entityManager.find(Role.class, id);
    }

    @Override
    public Role findByName(String name) {
        Query query = this.entityManager.createQuery("SELECT r FROM Role r where r.name = ?", Role.class);
        query.setParameter(1, name);
        Role role = null;
        try {
            role = (Role) query.getSingleResult();
        } catch (Exception e) {
            // to log
        }
        return role;
    }

    @Override
    public Role getDefaultRole() {
        return findByName(DEFAULT_ROLE_NAME);
    }

    @Override
    public Role getAdminRole() {
        return findByName(ADMIN_ROLE_NAME);
    }

}
