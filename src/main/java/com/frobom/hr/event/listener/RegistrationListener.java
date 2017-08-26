package com.frobom.hr.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.frobom.hr.event.RegistrationCompleteEvent;
import com.frobom.hr.service.SimpleMailService;

@Component
public class RegistrationListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private SimpleMailService mailService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        this.sendConfirmationMail(event);
    }

    private void sendConfirmationMail(RegistrationCompleteEvent event) {
        String email = event.getEmail();
        String token = event.getToken();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/account/confirm?token=" + token;
        String content = "Please confirm your email by clicking the link " + "http://localhost:8080" + confirmationUrl;

        mailService.send(email, subject, content);
    }

}
