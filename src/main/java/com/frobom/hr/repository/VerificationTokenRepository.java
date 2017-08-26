package com.frobom.hr.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.frobom.hr.entity.User;
import com.frobom.hr.entity.VerificationToken;

import java.util.Date;
import java.util.stream.Stream;

public interface VerificationTokenRepository  {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

    Stream<VerificationToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

    void save(VerificationToken verificationToken);

    @Modifying
    @Query("delete from VerificationToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);
}
