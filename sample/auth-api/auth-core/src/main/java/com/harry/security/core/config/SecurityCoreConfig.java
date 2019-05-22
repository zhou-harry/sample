package com.harry.security.core.config;

import com.harry.database.config.DynamicDataSourceConfig;
import com.harry.database.config.StaticDataSourceConfig;
import com.harry.security.core.properties.SecurityProperties;
import com.harry.security.core.repository.SessionValidateCodeRepository;
import com.harry.security.core.validate.code.ValidateCodeRepository;
import com.harry.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.harry.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.sql.DataSource;

/**
 * @author harry
 * @version 1.0
 * @title: SecurityCoreConfig
 * @description: 认证授权主配置
 * @date 2019/5/11 17:52
 */
@ComponentScan({"com.harry.security.core"})
@EnableConfigurationProperties(SecurityProperties.class)
@AutoConfigureAfter({StaticDataSourceConfig.class, DynamicDataSourceConfig.class})
public class SecurityCoreConfig {

    private final SecurityProperties securityProperties;

    private final DataSource defaultDataSource;

    public SecurityCoreConfig(SecurityProperties securityProperties, @Qualifier("defaultDataSource") DataSource defaultDataSource) {
        this.securityProperties = securityProperties;
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
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }

    /**
     * 此处容器会将RedisOperationsSessionRepository给注入进来
     *(spring redis启动器的场景有效，非启动器环境的话需要显示创建：RedisOperationsSessionRepository)
     * @param sessionRepository
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "spring.session.store-type", havingValue = "REDIS")
    public SpringSessionBackedSessionRegistry redisSessionRegistry(FindByIndexNameSessionRepository sessionRepository) {
        return new SpringSessionBackedSessionRegistry(sessionRepository);
    }

    @Bean
    @ConditionalOnMissingBean(ValidateCodeRepository.class)
    public ValidateCodeRepository validateCodeRepository() {
        return new SessionValidateCodeRepository();
    }

}
