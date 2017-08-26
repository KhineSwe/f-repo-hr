package com.frobom.hr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.frobom.hr.service.SimpleMailService;

@Service
public class SimpleMailServiceImpl implements SimpleMailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void send(String recipient, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipient);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        mailSender.send(mailMessage);
    }

}
