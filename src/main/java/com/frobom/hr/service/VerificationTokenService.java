package com.frobom.hr.service;

public interface VerificationTokenService {

    boolean isValidToken(String token);

}
