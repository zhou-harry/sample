package com.harry.security.app.config;

import com.harry.security.core.web.AbstractSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import static com.harry.security.core.constant.SecurityConstants.BASE_OAUTH2_AUTHORIZE;
import static com.harry.security.core.constant.SecurityConstants.BASE_SSO_LOGIN;

@Configuration
@Order(1)
public class AppSecurityConfig extends AbstractSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.requestMatchers()
                .antMatchers(BASE_SSO_LOGIN, BASE_OAUTH2_AUTHORIZE)
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();

    }

    /**
     * 用户认证信息获取的三种方式
     * auth.jdbcAuthentication()
     * auth.inMemoryAuthentication()
     * auth.userDetailsService()
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

}
