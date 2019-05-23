package com.harry.security.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import javax.sql.DataSource;

/**
 * localhost:8081/auth/oauth/authorize?response_type=code&client_id=R2dpxQ3vPrtfgF72&redirect_uri=http://localhost:8082/login/oauth2/code/&scope=user_info
 * localhost/oauth/authorize?response_type=code&client_id=user&redirect_uri=http://localhost:8089/oauth2/code/&scope=user_info
 *
 * @author harry
 * @version 1.0
 * @title: AuthServerConfig
 * @description: Oauth2认证服务配置
 * @date 2019/5/20 21:56
 */
@Configuration
@EnableAuthorizationServer
public class AppAuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private DataSource dataSource;


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
        clients.jdbc(dataSource)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }
}