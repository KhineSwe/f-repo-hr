package com.frobom.hr.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frobom.hr.entity.VerificationToken;
import com.frobom.hr.web.error.InvalidTokenException;
import com.frobom.hr.web.error.UserNotExistException;
import com.frobom.hr.dto.UserDto;
import com.frobom.hr.entity.Role;
import com.frobom.hr.entity.User;
import com.frobom.hr.repository.RoleRepository;
import com.frobom.hr.repository.UserRepository;
import com.frobom.hr.repository.VerificationTokenRepository;
import com.frobom.hr.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private VerificationTokenRepository tokenRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void save(User user) {
       this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(User user) {
        this.userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(int id) {
        return this.userRepository.findById(id);
    }

    @Override
    public List<User> filterByEmailContainingText(String text) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
    
    @Override
    @Transactional
    public void verifyUser(String token) throws InvalidTokenException {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            throw new InvalidTokenException();
        }
        verificationToken.updateToken(UUID.randomUUID().toString());
        // update?
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
    }
    
    @Override
    @Transactional
    public void createUserAndVerificationToken(User user, String token) {
        Role role = roleRepository.getDefaultRole();
        user.setRole(role);
        userRepository.save(user);
        VerificationToken verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }

    @Override
    @Transactional
    public void resetPassword(String token, String password) throws InvalidTokenException {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken != null) {
            User user = verificationToken.getUser();
            user.setPassword(password);
            userRepository.save(user);
        }
    }

    @Override
    @Transactional
    public void requestPasswordReset(String email, String token) throws UserNotExistException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotExistException();
        }
        VerificationToken verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }



}
