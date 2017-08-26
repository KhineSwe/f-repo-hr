package com.frobom.hr.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import com.frobom.hr.entity.SystemAccount;
import com.frobom.hr.repository.SystemAccountRepository;

@Repository
public class SystemAccountRepositoryImpl implements SystemAccountRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(SystemAccount systemAccount) {
	    if(systemAccount.getId() == null){
            this.entityManager.persist(systemAccount);
        }else{
            this.entityManager.merge(systemAccount);
        }
	}

	@Override
	public SystemAccount findById(int id) {
		return entityManager.find(SystemAccount.class, id);
	}

	@Override
	public SystemAccount findByLoginId(String loginId) {
		Query query = this.entityManager.createQuery("SELECT s FROM SystemAccount s WHERE s.loginId = ?",
				SystemAccount.class);
		query.setParameter(1, loginId);
		SystemAccount systemAccount = null;
		try {
			systemAccount = (SystemAccount) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return systemAccount;
	}

}
