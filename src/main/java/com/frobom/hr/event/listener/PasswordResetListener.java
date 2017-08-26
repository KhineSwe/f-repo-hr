package com.frobom.hr.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.frobom.hr.event.PasswordResetEvent;
import com.frobom.hr.service.SimpleMailService;

@Component
public class PasswordResetListener implements ApplicationListener<PasswordResetEvent> {

    @Autowired
    private SimpleMailService mailService;

    @Override
    public void onApplicationEvent(PasswordResetEvent event) {
        this.sendPasswordResetEmail(event);

    }

    private void sendPasswordResetEmail(PasswordResetEvent event) {
        String email = event.getEmail();
        String token = event.getToken();
        String subject = "Password Reset Email";
        String confirmationUrl = event.getAppUrl() + "/account/reset-password?token=" + token;
        String content = "Please confirm your email by clicking the link " + "http://localhost:8080" + confirmationUrl;

        mailService.send(email, subject, content);
    }

}
