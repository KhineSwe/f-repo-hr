package com.frobom.hr.form;

import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class ChangePasswordForm {
    @NotEmpty
	@Transient
	private String loginPassword;

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	@NotEmpty
	@Transient
	@Size(min = 4, max = 8, message = "loginPassword need to have only 4 to 8 characters")
	private String newPassword;

	public String getNewPassword() {
		return newPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	@NotEmpty
	@Transient
	private String confirmPassword;

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
