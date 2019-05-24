package com.harry.security.web.config;

import com.harry.security.core.properties.SecurityProperties;
import com.harry.security.core.validate.code.ValidateCodeRepository;
import com.harry.security.web.handler.WebAuthenticationFailureHandler;
import com.harry.security.web.handler.WebAuthenticationSuccessHandler;
import com.harry.security.web.handler.WebLogoutSuccessHandler;
import com.harry.security.web.session.WebInvalidSessionStrategy;
import com.harry.security.web.session.WebSessionExpiredStrategy;
import com.harry.security.web.session.repository.SessionValidateCodeRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @author harry
 * @version 1.0
 * @title: WebSecurityBeanConfig
 * @description: Web端Bean初始化配置
 * @date 2019/5/22 0:01
 */
@Configuration
@ComponentScan({"com.harry.security.web"})
public class WebSecurityBeanConfig {

    private final SecurityProperties securityProperties;

    public WebSecurityBeanConfig(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new WebLogoutSuccessHandler(securityProperties);
    }

    @Bean
    @ConditionalOnMissingBean(name = "authenticationSuccessHandler")
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new WebAuthenticationSuccessHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = "authenticationFailureHandler")
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new WebAuthenticationFailureHandler();
    }


    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new WebInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new WebSessionExpiredStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    @Bean
    @ConditionalOnMissingBean(ValidateCodeRepository.class)
    public ValidateCodeRepository validateCodeRepository() {
        return new SessionValidateCodeRepository();
    }

}
