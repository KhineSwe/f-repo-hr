package com.frobom.hr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/user*").authorizeRequests().anyRequest().hasRole("USER").and().formLogin()
				.loginPage("/user/login").usernameParameter("username").passwordParameter("password")
				.loginProcessingUrl("/user_login").defaultSuccessUrl("/userPage").and().logout()
				.logoutUrl("/user_logout").logoutSuccessUrl("/protectedLinks").deleteCookies("JSESSIONID").and()
				.exceptionHandling().accessDeniedPage("/403").and().csrf().disable();
	}
}
