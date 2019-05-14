package com.harry.security.config;

import com.harry.security.filter.ValidateCodeFilter;
import com.harry.security.properties.SecurityProperties;
import com.harry.security.validate.code.ValidateCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author harry
 * @version 1.0
 * @title: ValidateCodeSecurityConfig
 * @description: 验证码配置入口
 * @date 2019/5/12 0:32
 */
@Configuration
public class ValidateCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationFailureHandler baseAuthenticationFailureHandler;

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter(
                baseAuthenticationFailureHandler,
                securityProperties,
                validateCodeRepository
        );
        validateCodeFilter.afterPropertiesSet();

        http
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
