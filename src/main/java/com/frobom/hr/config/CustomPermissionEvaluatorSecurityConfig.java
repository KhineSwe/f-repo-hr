package com.frobom.hr.config;

import java.io.Serializable;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.Authentication;

import com.frobom.hr.service.CustomPermissionEvaluatorService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomPermissionEvaluatorSecurityConfig extends GlobalMethodSecurityConfiguration {


    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(getPermissionEvaluator());
        return expressionHandler;
    }
    
    public CustomPermissionEvaluatorService getPermissionEvaluator() {
        return new CustomPermissionEvaluatorService() {
            
            @Override
            public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
                // TODO Auto-generated method stub
                return false;
            }
        };
    }

}
