package com.frobom.hr.service;

import com.frobom.hr.entity.SystemAccount;

public interface SystemAccountService {
	
	SystemAccount findById(int id);
	
	SystemAccount findByLoginId(String loginId);
	
	void save(SystemAccount systemAccount);

}
