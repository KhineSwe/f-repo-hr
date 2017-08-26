package com.frobom.hr.repository.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.frobom.hr.entity.User;
import com.frobom.hr.entity.VerificationToken;
import com.frobom.hr.repository.VerificationTokenRepository;

@Repository
public class VerificationTokenRepositoryImplUpdate implements VerificationTokenRepository{

    @PersistenceContext
    private EntityManager entityManager;
   
    @Override
    public VerificationToken findByToken(String token) {
        List<VerificationToken> tokens = entityManager.createQuery("select v from VerificationToken v WHERE v.token=?", VerificationToken.class).setParameter(1, token).getResultList();

        if (tokens != null && tokens.size() > 0) {
            return tokens.get(0);
        }
        return null;
    }

    @Override
    public VerificationToken findByUser(User user) {
        return null;
    }

    @Override
    public Stream<VerificationToken> findAllByExpiryDateLessThan(Date now) {
        return null;
    }

    @Override
    public void deleteByExpiryDateLessThan(Date now) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteAllExpiredSince(Date now) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void save(VerificationToken verificationToken) {
	if (verificationToken.isNew()) {
	    entityManager.persist(verificationToken);
	} else {
	    entityManager.merge(verificationToken);
	}
    }
}
