package com.harry.security.config;

import com.harry.security.filter.ValidateCodeFilter;
import com.harry.security.properties.SecurityProperties;
import com.harry.security.constant.SecurityConstants;
import com.harry.security.repository.SessionValidateCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    private UserDetailsService dbUserDetailsService;

    private final PersistentTokenRepository persistentTokenRepository;
    private final SecurityProperties securityProperties;
    private final AuthenticationSuccessHandler baseAuthenticationSuccessHandler;
    private final AuthenticationFailureHandler baseAuthenticationFailureHandler;
    private final SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    private final SessionValidateCodeRepository validateCodeRepository;
    private final ValidateCodeSecurityConfig validateCodeSecurityConfig;

    public WebSecurityConfig(PersistentTokenRepository persistentTokenRepository, SecurityProperties securityProperties, AuthenticationSuccessHandler baseAuthenticationSuccessHandler, AuthenticationFailureHandler baseAuthenticationFailureHandler, SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig, SessionValidateCodeRepository validateCodeRepository, ValidateCodeSecurityConfig validateCodeSecurityConfig) {
        this.persistentTokenRepository = persistentTokenRepository;
        this.securityProperties = securityProperties;
        this.baseAuthenticationSuccessHandler = baseAuthenticationSuccessHandler;
        this.baseAuthenticationFailureHandler = baseAuthenticationFailureHandler;
        this.smsCodeAuthenticationSecurityConfig = smsCodeAuthenticationSecurityConfig;
        this.validateCodeRepository = validateCodeRepository;
        this.validateCodeSecurityConfig = validateCodeSecurityConfig;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("认证授权配置");

        http
                //自定义验证码过滤器
                .apply(validateCodeSecurityConfig)
                .and()//表单认证相关配置
                .formLogin()
                .loginPage(SecurityConstants.LOGIN_PAGE)
                .loginProcessingUrl(SecurityConstants.DEFAULT_SIGNIN_PROCESS_URL_FORM)
                .successHandler(baseAuthenticationSuccessHandler)
                .failureHandler(baseAuthenticationFailureHandler)
                .and()//Remember me 相关配置
                .rememberMe()
                .tokenRepository(persistentTokenRepository)
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(dbUserDetailsService)
                .and()//授权相关的配置
                .authorizeRequests()
                .antMatchers(SecurityConstants.MATCHERS).permitAll()
                .antMatchers(securityProperties.getBrowser().getSigninPageUrl()).permitAll()
                .anyRequest()
                .authenticated()
                .and().csrf().disable()
                //导入短信验证配置
                .apply(smsCodeAuthenticationSecurityConfig)
        ;
    }
}
