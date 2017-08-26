package com.frobom.hr.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "login_user_account")
public class LoginUserAccount extends BaseEntity {

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "system_id")
	private SystemList systemList;

	@NotEmpty
	@Column(name = "user_id", unique = false)
	private String userId;

	@NotEmpty
	@Column(name = "user_pwd", unique = false)
	private String userPwd;

	@Column(name = "user_name", unique = true)
	private String userName;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "deleted", unique = false)
	private String deleted;

	public SystemList getSystemList() {
		return systemList;
	}

	public void setSystemList(SystemList systemList) {
		this.systemList = systemList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

}
