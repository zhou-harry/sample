package com.harry.security.app.config;

import com.harry.security.app.handler.AppAuthenticationFailureHandler;
import com.harry.security.app.handler.AppAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author harry
 * @version 1.0
 * @title: AppSecurityBeanConfig
 * @description: TODO
 * @date 2019/5/22 9:57
 */
@Configuration
@ComponentScan({"com.harry.security.app"})
public class AppSecurityBeanConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AppAuthenticationFailureHandler appAuthenticationFailureHandler(){
        return new AppAuthenticationFailureHandler();
    }

    @Bean
    public AppAuthenticationSuccessHandler appAuthenticationSuccessHandler(){
        return new AppAuthenticationSuccessHandler();
    }

}
