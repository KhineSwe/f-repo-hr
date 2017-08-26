package com.frobom.hr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frobom.hr.entity.VerificationToken;
import com.frobom.hr.repository.VerificationTokenRepository;
import com.frobom.hr.service.VerificationTokenService;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Override
    public boolean isValidToken(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        return verificationToken != null? true : false;
    }
}
