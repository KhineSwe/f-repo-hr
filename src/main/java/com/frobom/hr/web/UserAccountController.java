package com.frobom.hr.web;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.frobom.hr.event.PasswordResetEvent;
import com.frobom.hr.form.PasswordResetEmailForm;
import com.frobom.hr.form.PasswordResetForm;
import com.frobom.hr.service.UserService;
import com.frobom.hr.service.VerificationTokenService;
import com.frobom.hr.web.error.InvalidTokenException;
import com.frobom.hr.web.error.UserNotExistException;

@Controller
public class UserAccountController {

    @Autowired
    private PasswordEncoder passwordEnconder;

    public void setPasswordEnconder(PasswordEncoder passwordEnconder) {
	this.passwordEnconder = passwordEnconder;
    }

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @RequestMapping(value = "/account/confirm", method = RequestMethod.GET)
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {
        try {
	    userService.verifyUser(token);
	} catch (InvalidTokenException e) {
	    e.printStackTrace();
	    return "invalidToken";
	}
        return "redirect:/login.html?lang=" + request.getLocale().getLanguage();
    }

    @RequestMapping(value = "/account/forget-password", method = RequestMethod.GET)
    public String forgetPassword(Model model) {
        model.addAttribute("passwordResetEmailForm", new PasswordResetEmailForm());
        return "passwordResetEmail";
    }

    @RequestMapping(value = "/account/forget-password", method = RequestMethod.POST)
    public String requestPasswordResetLink(@Validated @ModelAttribute PasswordResetEmailForm passwordForgetForm,
	    BindingResult result, Model model, WebRequest request) {
	// validation can be done here
	String token = UUID.randomUUID().toString();
	try {
	    userService.requestPasswordReset(passwordForgetForm.getEmail(), token);
	} catch (UserNotExistException e1) {
	    e1.printStackTrace();
	    return "userNotExist";
	}
	try {
	    eventPublisher.publishEvent(new PasswordResetEvent(passwordForgetForm.getEmail(), token,
		    request.getLocale(), request.getContextPath()));
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return "passwordResetRequested";
    }

    @RequestMapping(value = "/account/reset-password", method = RequestMethod.GET)
    public String requestPasswordResetForm(WebRequest request, Model model, @RequestParam("token") String token) {
        if (verificationTokenService.isValidToken(token)) {
            PasswordResetForm form = new PasswordResetForm();
            form.setToken(token);
            model.addAttribute("passwordResetForm", form);
            return "passwordResetForm";
        }

        return "passwordResetError";
    }

    @RequestMapping(value = "/account/reset-password", method = RequestMethod.POST)
    public String resetPassword(@Validated @ModelAttribute PasswordResetForm passwordResetForm, BindingResult result, Model model, WebRequest request) {
        if (result.hasErrors()) {
            return "passwordResetForm";
        }
        try {
            userService.resetPassword(passwordResetForm.getToken(), passwordEnconder.encode(passwordResetForm.getPassword()));
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }
        return "redirect:/login.html?lang=" + request.getLocale().getLanguage();
    }
}
