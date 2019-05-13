package com.harry.security.config;

import com.harry.database.config.DynamicDataSourceConfig;
import com.harry.database.config.StaticDataSourceConfig;
import com.harry.security.properties.SecurityProperties;
import com.harry.security.repository.SessionValidateCodeRepository;
import com.harry.security.validate.code.ValidateCodeRepository;
import com.harry.security.validate.code.sms.DefaultSmsCodeSender;
import com.harry.security.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author harry
 * @version 1.0
 * @title: SecurityCoreConfig
 * @description: 权限认证核心配置
 * @date 2019/5/11 17:52
 */
@ComponentScan({"com.harry.security"})
@EnableConfigurationProperties(SecurityProperties.class)
@AutoConfigureAfter({StaticDataSourceConfig.class, DynamicDataSourceConfig.class})
public class SecurityCoreConfig {

    private final DataSource defaultDataSource;

    public SecurityCoreConfig(@Qualifier("defaultDataSource") DataSource defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(defaultDataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }

    @Bean
    @ConditionalOnMissingBean(ValidateCodeRepository.class)
    public SessionValidateCodeRepository validateCodeRepository(){
        return new SessionValidateCodeRepository();
    }

}
