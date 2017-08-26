package com.frobom.hr.service;

import java.util.List;

import com.frobom.hr.dto.UserDto;
import com.frobom.hr.entity.User;
import com.frobom.hr.web.error.InvalidTokenException;
import com.frobom.hr.web.error.UserNotExistException;

public interface UserService {

    // wrap normal repository methods

    void save(User user);

    void delete(User user);

    List<User> findAll();

    User findByEmail(String email);

    User findById(int id);

    List<User> filterByEmailContainingText(String text);

    UserDto convertToDto(User user);

    void verifyUser(String token) throws InvalidTokenException;

    void requestPasswordReset(String email, String token) throws UserNotExistException;

    void resetPassword(String token, String password) throws InvalidTokenException;

    void createUserAndVerificationToken(User user, String token);
}
