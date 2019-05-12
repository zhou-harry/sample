package com.harry.zuul.security.config;

import com.harry.security.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.harry.security.config.ValidateCodeSecurityConfig;
import com.harry.security.constant.SecurityConstants;
import com.harry.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    private UserDetailsService userDetailsService;

    private final PersistentTokenRepository persistentTokenRepository;
    private final SecurityProperties securityProperties;
    private final AuthenticationSuccessHandler baseAuthenticationSuccessHandler;
    private final AuthenticationFailureHandler baseAuthenticationFailureHandler;
    private final SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    private final ValidateCodeSecurityConfig validateCodeSecurityConfig;
    private final SpringSocialConfigurer socialSecurityConfig;

    public WebSecurityConfig(PersistentTokenRepository persistentTokenRepository, SecurityProperties securityProperties, AuthenticationSuccessHandler baseAuthenticationSuccessHandler, AuthenticationFailureHandler baseAuthenticationFailureHandler, SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig, ValidateCodeSecurityConfig validateCodeSecurityConfig, SpringSocialConfigurer socialSecurityConfig) {
        this.persistentTokenRepository = persistentTokenRepository;
        this.securityProperties = securityProperties;
        this.baseAuthenticationSuccessHandler = baseAuthenticationSuccessHandler;
        this.baseAuthenticationFailureHandler = baseAuthenticationFailureHandler;
        this.smsCodeAuthenticationSecurityConfig = smsCodeAuthenticationSecurityConfig;
        this.validateCodeSecurityConfig = validateCodeSecurityConfig;
        this.socialSecurityConfig = socialSecurityConfig;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("认证授权配置");
        http
                //自定义验证码过滤器
                .apply(validateCodeSecurityConfig)
                .and()//导入短信验证配置
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()//社交验证配置
                .apply(socialSecurityConfig)
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
                .userDetailsService(userDetailsService)
                .and()//授权相关的配置
                .authorizeRequests()
                .antMatchers(SecurityConstants.MATCHERS).permitAll()
                .antMatchers(securityProperties.getBrowser().getSignInUrl()).permitAll()
                .antMatchers(securityProperties.getBrowser().getSignUpUrl()).permitAll()
                .antMatchers("/user/regist").permitAll()
                .anyRequest()
                .authenticated()
                .and().csrf().disable()
        ;
    }
}
