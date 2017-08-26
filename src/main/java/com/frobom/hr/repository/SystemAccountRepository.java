package com.frobom.hr.repository;

import com.frobom.hr.entity.SystemAccount;

public interface SystemAccountRepository {

	void save(SystemAccount systemAccount);

	SystemAccount findById(int id);
	
	SystemAccount findByLoginId(String loginId);
	
}
