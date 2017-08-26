package com.frobom.hr.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.frobom.hr.entity.User;
import com.frobom.hr.repository.UserRepository;

@Repository
public class UserRepositoryImplUpdate implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(User user) {
        if (user.getId() == null) {
            this.entityManager.persist(user);
        } else {
            this.entityManager.merge(user);
        }
    }

    @Override
    public void delete(User user) {
        this.entityManager.remove(user);
    }

    @Override
    public List<User> findAll() {
        Query query = this.entityManager.createQuery("SELECT u from User u", User.class);
        return query.getResultList();
    }

    @Override
    public User findByEmail(String email) {
        Query query = this.entityManager.createQuery("SELECT u from User u WHERE u.email = ?", User.class);
        query.setParameter(1, email);
        User user = null;
        try {
            user = (User) query.getSingleResult();
        } catch (Exception e) {
            // to log
        }
        return user;
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> filterByEmailContainingText(String text) {
        return null;
    }

}
