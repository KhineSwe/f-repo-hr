package com.frobom.hr.repository.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import com.frobom.hr.entity.LoginUserAccount;
import com.frobom.hr.repository.LoginUserAccountRepository;

@Repository
public class LoginUserAccountRepositoryImpl implements LoginUserAccountRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void save(LoginUserAccount user) {
		if (user.getId() == null) {
			entityManager.persist(user);
		} else {
			entityManager.merge(user);
		}
	}

	@Override
	public void delete(LoginUserAccount loginUser) {

	}

	@Override
	public List<LoginUserAccount> findBySystem(int id) {
		Query query = entityManager.createQuery("select u from LoginUserAccount u where u.systemList.id = :id",
				LoginUserAccount.class);
		query.setParameter("id", id);
		return query.getResultList();
	}

}
