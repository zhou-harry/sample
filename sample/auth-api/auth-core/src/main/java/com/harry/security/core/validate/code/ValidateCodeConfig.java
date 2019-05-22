package com.harry.security.core.validate.code;

import com.harry.security.core.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author harry
 * @version 1.0
 * @title: ValidateCodeConfig
 * @description: 验证码配置入口
 * @date 2019/5/12 0:32
 */
@Configuration
public class ValidateCodeConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
