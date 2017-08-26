package com.frobom.hr.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.frobom.hr.entity.User;
import com.frobom.hr.service.UserService;

@Component("userFormValidator")
public class UserFormValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (!user.getPassword().equals(user.getConfirmedPassword())) {
            errors.rejectValue("confirmedPassword", "password.notMatched", "Passwords do not match.");
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "email.alreadyExists", "Email already taken.");
        }
    }

}
