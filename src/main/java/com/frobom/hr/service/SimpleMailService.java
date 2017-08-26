package com.frobom.hr.service;

public interface SimpleMailService {

    void send(String recipient, String subject, String content);
}
