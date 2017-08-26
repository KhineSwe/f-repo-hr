package com.frobom.hr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@Order(1)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("adminDetailsService")
    private UserDetailsService userDetailsService;

    @Bean("adminAuthenticationProvider")
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Autowired
    @Qualifier("adminAuthenticationProvider")
    private DaoAuthenticationProvider adminAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(adminAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/admin/login", "/resources/**").permitAll().antMatchers("/admin**").access("hasRole('ADMIN')").anyRequest().authenticated()
                .and().formLogin().loginPage("/admin/login").usernameParameter("loginId").passwordParameter("loginPassword").loginProcessingUrl("/admin_login")
                .defaultSuccessUrl("/admin")
                .and().logout().logoutUrl("/admin_logout").logoutSuccessUrl("/admin/login?logout").deleteCookies("JSESSIONID")
                .and().exceptionHandling().accessDeniedPage("/403")
                .and().csrf();
    }
}
