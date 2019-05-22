package com.harry.security.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * localhost:8081/auth/oauth/authorize?response_type=code&client_id=R2dpxQ3vPrtfgF72&redirect_uri=http://localhost:8082/login/oauth2/code/&scope=user_info
 * localhost:8081/auth/oauth/token?grant_type=authorization_code&client_id=R2dpxQ3vPrtfgF72&client_secret=fDw7Mpkk5czHNuSRtmhGmAGL42CaxQB9&code=uAStRI&scope=user_info&redirect_uri=http://localhost:8082/login/oauth2/code/
 * localhost:8081/auth/user/me?access_token=8b2094a7-5cd5-4edf-9289-fb73fac00a88
 * @author harry
 * @version 1.0
 * @title: AuthServerConfig
 * @description: TODO
 * @date 2019/5/20 21:56
 */
@Configuration
@EnableAuthorizationServer
public class AppAuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${harry.oauth.clientId}")
    private String clientID;
    @Value("${harry.oauth.clientSecret}")
    private String clientSecret;
    @Value("${harry.oauth.redirectUris}")
    private String redirectURLs;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public void configure(
            AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientID)
                .secret(clientSecret)//passwordEncoder.encode(clientSecret)
                .authorizedGrantTypes("authorization_code", "refresh_token", "password")
                .scopes("user_info")
                .autoApprove(true)
                .redirectUris(redirectURLs)
        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }
}