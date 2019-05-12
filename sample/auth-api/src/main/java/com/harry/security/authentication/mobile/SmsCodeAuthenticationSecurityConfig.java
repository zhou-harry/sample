package com.harry.security.authentication.mobile;

import com.harry.security.authentication.mobile.SmsCodeAuthenticationFilter;
import com.harry.security.authentication.mobile.SmsCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author harry
 * @version 1.0
 * @title: SmsCodeAuthenticationSecurityConfig
 * @description: 短信验证码配置
 * @date 2019/5/11 17:28
 */
@Configuration
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final AuthenticationSuccessHandler baseAuthenticationSuccessHandler;
    private final AuthenticationFailureHandler baseAuthenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    public SmsCodeAuthenticationSecurityConfig(AuthenticationSuccessHandler baseAuthenticationSuccessHandler, AuthenticationFailureHandler baseAuthenticationFailureHandler) {
        this.baseAuthenticationSuccessHandler = baseAuthenticationSuccessHandler;
        this.baseAuthenticationFailureHandler = baseAuthenticationFailureHandler;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        //1，配置短信验证码过滤器
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        //设置认证失败成功处理器
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(baseAuthenticationSuccessHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(baseAuthenticationFailureHandler);

        //配置provider
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider(userDetailsService);
        http
                .authenticationProvider(smsCodeAuthenticationProvider)
                .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
