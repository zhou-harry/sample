package com.harry.security.app.config;

import com.harry.security.app.handler.AppAuthenticationFailureHandler;
import com.harry.security.app.handler.AppAuthenticationSuccessHandler;
import com.harry.security.app.repository.RedisValidateCodeRepository;
import com.harry.security.app.social.AppSocialAuthenticationFilterPostProcessor;
import com.harry.security.core.social.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(AppAuthenticationFailureHandler.class)
    public AppAuthenticationFailureHandler appAuthenticationFailureHandler(){
        return new AppAuthenticationFailureHandler();
    }

    @Bean
    @ConditionalOnMissingBean(AppAuthenticationSuccessHandler.class)
    public AppAuthenticationSuccessHandler appAuthenticationSuccessHandler(){
        return new AppAuthenticationSuccessHandler();
    }

    @Bean
    public RedisValidateCodeRepository redisValidateCodeRepository(){
        return new RedisValidateCodeRepository(redisTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(value = SocialAuthenticationFilterPostProcessor.class)
    public SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor() {
        return new AppSocialAuthenticationFilterPostProcessor();
    }

}
