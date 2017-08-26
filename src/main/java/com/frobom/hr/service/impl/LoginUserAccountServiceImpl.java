package com.frobom.hr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frobom.hr.entity.LoginUserAccount;
import com.frobom.hr.repository.LoginUserAccountRepository;
import com.frobom.hr.service.LoginUserAccountService;

@Service
public class LoginUserAccountServiceImpl implements LoginUserAccountService {
	
	@Autowired
	private LoginUserAccountRepository loginUserAccountRepository;

	@Override
	@Transactional
	public void save(LoginUserAccount loginUser) {
		this.loginUserAccountRepository.save(loginUser);
		
	}

}
