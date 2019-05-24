package com.harry.security.web.config;

import com.harry.security.core.authentication.mobile.SmsCodeAuthenticationConfig;
import com.harry.security.core.authroize.manager.AuthorizeConfigManager;
import com.harry.security.core.constant.SecurityConstants;
import com.harry.security.core.properties.SecurityProperties;
import com.harry.security.core.validate.code.ValidateCodeConfig;
import com.harry.security.core.web.AbstractSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class WebSecurityConfig extends AbstractSecurityConfig implements WebMvcConfigurer {

    @Value("server.servlet.session.cookie.name")
    private String cookieName;
    @Autowired
    private UserDetailsService userDetailsService;

    private final PersistentTokenRepository persistentTokenRepository;
    private final SecurityProperties securityProperties;
    private final SmsCodeAuthenticationConfig smsCodeAuthenticationConfig;
    private final ValidateCodeConfig validateCodeSecurityConfig;
    private final SpringSocialConfigurer socialSecurityConfig;
    private final SessionInformationExpiredStrategy expiredSessionStrategy;
    private final InvalidSessionStrategy invalidSessionStrategy;
    private final SpringSessionBackedSessionRegistry redisSessionRegistry;
    private final LogoutSuccessHandler logoutSuccessHandler;
    private final AuthorizeConfigManager authorizeConfigManager;

    public WebSecurityConfig(PersistentTokenRepository persistentTokenRepository, SecurityProperties securityProperties, SmsCodeAuthenticationConfig smsCodeAuthenticationConfig, ValidateCodeConfig validateCodeSecurityConfig, SpringSocialConfigurer socialSecurityConfig, SessionInformationExpiredStrategy expiredSessionStrategy, InvalidSessionStrategy invalidSessionStrategy, SpringSessionBackedSessionRegistry redisSessionRegistry, LogoutSuccessHandler logoutSuccessHandler, AuthorizeConfigManager authorizeConfigManager) {
        this.persistentTokenRepository = persistentTokenRepository;
        this.securityProperties = securityProperties;
        this.smsCodeAuthenticationConfig = smsCodeAuthenticationConfig;
        this.validateCodeSecurityConfig = validateCodeSecurityConfig;
        this.socialSecurityConfig = socialSecurityConfig;
        this.expiredSessionStrategy = expiredSessionStrategy;
        this.invalidSessionStrategy = invalidSessionStrategy;
        this.redisSessionRegistry = redisSessionRegistry;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.authorizeConfigManager = authorizeConfigManager;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.debug("认证授权配置,cookieName: "+cookieName);
        //表单认证相关配置
        this.applyPasswordAuthConfig(http);

        http
                //自定义验证码过滤器
                .apply(validateCodeSecurityConfig)
                .and()//短信验证配置
                .apply(smsCodeAuthenticationConfig)
                .and()//社交验证配置
                .apply(socialSecurityConfig)
                .and()//Remember me 相关配置
                .rememberMe()
                .tokenRepository(persistentTokenRepository)
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()//Session 相关配置
                .sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy)
                .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                .expiredUrl(securityProperties.getBrowser().getSession().getSessionInvalidUrl())
                .expiredSessionStrategy(expiredSessionStrategy)
                .sessionRegistry(redisSessionRegistry)
                .and()
                .and()//授权相关的配置
                .authorizeRequests()
                .antMatchers(SecurityConstants.MATCHERS).permitAll()
                .antMatchers(securityProperties.getBrowser().getSignInUrl()).permitAll()
                .antMatchers(securityProperties.getBrowser().getSignUpUrl()).permitAll()
                .antMatchers(securityProperties.getBrowser().getSignOutUrl()).permitAll()
                .antMatchers(securityProperties.getBrowser().getSession().getSessionInvalidUrl()).permitAll()
                .antMatchers("/user/regist").permitAll()
                .anyRequest()
                .authenticated()
                .and()//登出
                .logout()
                .logoutUrl(securityProperties.getBrowser().getLogoutPage())
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies(cookieName)
                .and().csrf().disable()
        ;
        authorizeConfigManager.config(http.authorizeRequests());
    }

}
