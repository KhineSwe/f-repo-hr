package com.frobom.hr.web;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.frobom.hr.entity.User;
import com.frobom.hr.event.RegistrationCompleteEvent;
import com.frobom.hr.service.UserService;
import com.frobom.hr.validator.UserFormValidator;

@Controller
public class UserController {

    @Autowired
    private PasswordEncoder passwordEnconder;

    public void setPasswordEnconder(PasswordEncoder passwordEnconder) {
        this.passwordEnconder = passwordEnconder;
    }

    @Autowired
    @Qualifier("userFormValidator")
    private UserFormValidator userFormValidator;

    @Autowired
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/google", method = RequestMethod.GET)
    public String showSignupFormNew(Model model) {
        model.addAttribute("user", new User());
        return "google";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated @ModelAttribute User user, BindingResult result, Model model, WebRequest request) {
        if (result.hasErrors()) {
            return "signup";
        }

        userFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "signup";
        }
        String token = UUID.randomUUID().toString();
        user.setPassword(passwordEnconder.encode(user.getPassword()));
        userService.createUserAndVerificationToken(user, token);
        try {
            eventPublisher.publishEvent(new RegistrationCompleteEvent(user.getEmail(), token, request.getLocale(), request.getContextPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("users", this.userService.findAll());
        return "users";
    }

    @RequestMapping(value = "users/{id}", method = RequestMethod.GET)
    public String showDetails(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "userDetails";
    }

}
