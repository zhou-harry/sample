package com.harry.security.web;

import com.harry.security.constant.SecurityConstants;
import com.harry.security.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author harry
 * @version 1.0
 * @title: AbstractWebSecurityConfig
 * @description: 认证配置父类
 * @date 2019/5/16 21:54
 */
public class AbstractWebSecurityConfig extends WebSecurityConfigurerAdapter {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     * 表单通用配置
     *
     * @param http
     * @throws Exception
     */
    protected void applyPasswordAuthConfig(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(securityProperties.getBrowser().getLoginPage())
                .loginProcessingUrl(securityProperties.getBrowser().getLoginProcessingUrl())
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);
    }

    /**
     * 授权通用配置
     * @param http
     * @throws Exception
     */
    protected void applyAuthorizeConfig(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .antMatchers(SecurityConstants.MATCHERS).permitAll()
                .antMatchers(securityProperties.getBrowser().getSignInUrl()).permitAll()
                .antMatchers(securityProperties.getBrowser().getSignUpUrl()).permitAll()
                .antMatchers(securityProperties.getBrowser().getSession().getSessionInvalidUrl()).permitAll()
                .antMatchers("/user/regist").permitAll()
        ;

        String signOutUrl = securityProperties.getBrowser().getSignOutUrl();
        if (StringUtils.isBlank(signOutUrl)) {
            http.authorizeRequests().antMatchers(signOutUrl).permitAll();
        }
    }
}
