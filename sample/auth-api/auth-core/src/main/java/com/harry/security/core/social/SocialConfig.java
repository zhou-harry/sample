package com.harry.security.core.social;

import com.harry.security.core.constant.SecurityConstants;
import com.harry.security.core.properties.SecurityProperties;
import com.harry.security.core.social.qq.config.QQAutoConfig;
import com.harry.security.core.social.weixin.config.WeixinAutoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
@ConditionalOnBean({QQAutoConfig.class, WeixinAutoConfig.class})
public class SocialConfig extends SocialConfigurerAdapter {

    @EnableSocial
    public class ConfigAdapter extends SocialConfigurerAdapter {

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
    }
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;



    @Bean
    public SpringSocialConfigurer socialSecurityConfig() {
        CustomerSocialConfigurer configurer = new CustomerSocialConfigurer(securityProperties.getSocial().getFilterProcessUrl());
        configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        return configurer;
    }

}