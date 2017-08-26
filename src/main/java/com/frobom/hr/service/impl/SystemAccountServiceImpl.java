package com.frobom.hr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frobom.hr.entity.SystemAccount;
import com.frobom.hr.repository.SystemAccountRepository;
import com.frobom.hr.service.SystemAccountService;

@Service
public class SystemAccountServiceImpl implements SystemAccountService {

	@Autowired
	private SystemAccountRepository systemAccountRepository;

	@Override
	@Transactional
	public void save(SystemAccount systemAccount) {
		this.systemAccountRepository.save(systemAccount);
	}
	
	@Override
	@Transactional
	public SystemAccount findById(int id) {
	    return this.systemAccountRepository.findById(id);
    }
	
	@Override
    @Transactional
    public SystemAccount findByLoginId(String loginId) {
        return this.systemAccountRepository.findByLoginId(loginId);
    }

}
