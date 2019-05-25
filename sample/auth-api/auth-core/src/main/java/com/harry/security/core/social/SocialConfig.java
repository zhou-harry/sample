package com.harry.security.core.social;

import com.harry.security.core.constant.SecurityConstants;
import com.harry.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author harry
 * @version 1.0
 * @title: SocialConfig
 * @description: 第三方接入配置入口
 * @date 2019/5/12 15:57
 */
@Configuration("socialConfig")
@EnableSocial
@ConditionalOnProperty(name = "harry.security.social.open", havingValue = "true")
public class SocialConfig extends SocialConfigurerAdapter {

    Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private DataSource dataSource;
    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;
    @Autowired(required = false)
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;


    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        jdbcUsersConnectionRepository.setTablePrefix(SecurityConstants.DEFAULT_SOCIAL_USER_CONNECTION_PREFIX);
        // 根据connection的数据创建userId
        if (connectionSignUp != null) {
            jdbcUsersConnectionRepository.setConnectionSignUp(connectionSignUp);
        }
        return jdbcUsersConnectionRepository;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(
                connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator)
        );
    }

    /**
     * 提供查询社交账户信息服务，绑定/解绑等服务<br/>
     * 为什么在ConnectController中已经注明了@Controller，而在此处还需要显示的声明一个@Bean对象<br/>
     * 我的理解是因为auth-api 默认只扫描：@ComponentScan({"com.harry.security"})
     *
     * @param connectionFactoryLocator
     * @param connectionRepository
     * @return
     */
    @Bean
    public ConnectController connectController(
            ConnectionFactoryLocator connectionFactoryLocator,
            ConnectionRepository connectionRepository) {
        return new ConnectController(connectionFactoryLocator, connectionRepository);
    }

    @Bean(SecurityConstants.BASE_SOCIAL_CONFIGURER)
    public SpringSocialConfigurer socialSecurityConfig() {
        BaseSocialConfigurer configurer = new BaseSocialConfigurer(
                securityProperties.getSocial().getFilterProcessUrl(),
                socialAuthenticationFilterPostProcessor
        );
        configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        return configurer;
    }

}
