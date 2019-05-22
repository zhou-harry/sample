package com.harry.security.core.authentication.openid;

import com.harry.security.core.properties.SecurityProperties;
import com.harry.security.core.social.SocialConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * @author harry
 * @version 1.0
 * @title: OpenIdAuthenticationConfig
 * @description: OpenId认证Config
 * @date 2019/5/20 11:15
 */
@Configuration
@ConditionalOnBean({SocialConfig.ConfigAdapter.class})
public class OpenIdAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private SocialUserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        OpenIdAuthenticationFilter openIdAuthenticationFilter = new OpenIdAuthenticationFilter(
                securityProperties.getBrowser().getSigninProcessUrlOpenId()
        );

        openIdAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        openIdAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        openIdAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        OpenIdAuthenticationProvider provider = new OpenIdAuthenticationProvider(userDetailsService,usersConnectionRepository);

        http
                .authenticationProvider(provider)
                .addFilterAfter(openIdAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
