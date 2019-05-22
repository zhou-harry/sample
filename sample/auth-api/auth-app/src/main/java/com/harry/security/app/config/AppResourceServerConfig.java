package com.harry.security.app.config;

import com.harry.security.core.constant.SecurityConstants;
import com.harry.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author harry
 * @version 1.0
 * @title: AppResourceServerConfig
 * @description: Oauth2资源服务器
 * @date 2019/5/20 11:31
 */
@Configuration
@EnableResourceServer
public class AppResourceServerConfig extends ResourceServerConfigurerAdapter {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        logger.debug("App认证资源配置");
        //表单认证相关配置
        http
                //自定义验证码过滤器
//                .apply(validateCodeSecurityConfig)
//                .and()//短信验证配置
//                .apply(smsCodeAuthenticationConfig)
//                .and()//社交验证配置
//                .apply(socialSecurityConfig)
//                .and()//授权相关的配置
                .formLogin()
                .loginPage(securityProperties.getBrowser().getLoginPage())
                .loginProcessingUrl(securityProperties.getBrowser().getSigninProcessUrlForm())
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers(SecurityConstants.MATCHERS).permitAll()
                .antMatchers(securityProperties.getBrowser().getSignInUrl()).permitAll()
                .antMatchers(securityProperties.getBrowser().getSignUpUrl()).permitAll()
                .antMatchers(securityProperties.getBrowser().getSession().getSessionInvalidUrl()).permitAll()
                .antMatchers("/user/regist").permitAll()
                .anyRequest()
                .authenticated()
                .and().csrf().disable()
        ;
    }

}
