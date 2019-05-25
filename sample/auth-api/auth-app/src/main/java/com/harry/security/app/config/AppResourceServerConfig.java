package com.harry.security.app.config;

import com.harry.security.core.authentication.mobile.SmsCodeAuthenticationConfig;
import com.harry.security.core.authentication.openid.OpenIdAuthenticationConfig;
import com.harry.security.core.authorize.manager.AuthorizeConfigManager;
import com.harry.security.core.properties.SecurityProperties;
import com.harry.security.core.validate.code.ValidateCodeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author harry
 * @version 1.0
 * @title: AppResourceServerConfig
 * @description: Oauth2资源服务配置
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
    @Autowired
    private SmsCodeAuthenticationConfig smsCodeAuthenticationConfig;
    @Autowired
    private ValidateCodeConfig validateCodeSecurityConfig;
    @Autowired
    private SpringSocialConfigurer socialSecurityConfig;
    @Autowired
    private OpenIdAuthenticationConfig openIdAuthenticationConfig;
    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;
    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        logger.debug("App认证资源配置");
        http
                .apply(validateCodeSecurityConfig).and()//自定义验证码过滤器
                .apply(smsCodeAuthenticationConfig).and()//手机号认证配置
                .apply(socialSecurityConfig).and()//社交验证配置
                .apply(openIdAuthenticationConfig).and()//OPenId验证配置
                .formLogin()
                .loginPage(securityProperties.getBrowser().getLoginPage())
                .loginProcessingUrl(securityProperties.getBrowser().getSigninProcessUrlForm())
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and().csrf().disable()
        ;
        authorizeConfigManager.config(http.authorizeRequests());
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.expressionHandler(expressionHandler);
    }

}
