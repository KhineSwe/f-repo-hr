package com.frobom.hr.repository;

import java.util.List;

import com.frobom.hr.entity.LoginUserAccount;
import com.frobom.hr.entity.SystemList;

public interface LoginUserAccountRepository {
	
	void save(LoginUserAccount user);

	void delete(LoginUserAccount user);

	List<LoginUserAccount> findBySystem(int id);

}
