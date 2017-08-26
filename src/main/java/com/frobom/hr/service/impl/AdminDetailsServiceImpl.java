package com.frobom.hr.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.frobom.hr.entity.SystemAccount;
import com.frobom.hr.service.SystemAccountService;

@Service("adminDetailsService")
public class AdminDetailsServiceImpl implements UserDetailsService {

	@Autowired
	public SystemAccountService systemAccountService;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		boolean enable = true;

		try {
			SystemAccount systemAccount = systemAccountService.findByLoginId(loginId);

			if (systemAccount == null) {
				throw new UsernameNotFoundException("No user found with these id: " + loginId);
			}
			System.out.println("Admin passwoed in detail is:" + systemAccount.getLoginPassword());
			return new org.springframework.security.core.userdetails.User(systemAccount.getLoginId(),
					systemAccount.getLoginPassword(), enable, accountNonExpired, credentialsNonExpired,
					accountNonLocked, getGrantedAuthorities(systemAccount));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private List<GrantedAuthority> getGrantedAuthorities(SystemAccount systemAccount) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return authorities;
	}
}
