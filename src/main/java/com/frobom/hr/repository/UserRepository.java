package com.frobom.hr.repository;

import java.util.List;

import com.frobom.hr.entity.User;

public interface UserRepository {

    void save(User user);

    void delete(User user);

    List<User> findAll();

    User findByEmail(String email);

    User findById(int id);

    List<User> filterByEmailContainingText(String text);

}
