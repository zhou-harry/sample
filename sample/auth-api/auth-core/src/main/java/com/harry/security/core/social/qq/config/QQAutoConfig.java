package com.harry.security.core.social.qq.config;

import com.harry.security.core.properties.SecurityProperties;
import com.harry.security.core.properties.SocialProperties;
import com.harry.security.core.social.qq.connet.QQConnectionFactory;
import com.harry.security.core.social.view.CustomerConnectView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.web.servlet.View;

/**
 * @author harry
 * @version 1.0
 * @title: QQAutoConfig
 * @description: QQ登录配置
 * @date 2019/5/12 16:43
 */
@Configuration
@ConditionalOnProperty(name = "harry.security.social.open", havingValue = "true")
public class QQAutoConfig implements SocialConfigurer {

    Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        SocialProperties.QQProperties qqConfig = securityProperties.getSocial().getQq();
        QQConnectionFactory connectionFactory = new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
        connectionFactoryConfigurer.addConnectionFactory(connectionFactory);
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return null;
    }

    /**
     * 绑定/解绑
     * @return
     */
    @Bean({"connect/qqConnected", "connect/qqConnect"})
    @ConditionalOnMissingBean(name = "qqConnectedView")
    public View qqConnectedView() {
        return new CustomerConnectView();
    }
}
