package com.harry.security.core.social.weixin.config;

import com.harry.security.core.properties.SecurityProperties;
import com.harry.security.core.properties.SocialProperties;
import com.harry.security.core.social.view.CustomerConnectView;
import com.harry.security.core.social.weixin.connet.WeixinConnectionFactory;
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
 * @title: WeixinAutoConfig
 * @description: 微信登录配置
 * @date 2019/5/12 18:51
 */
@Configuration
@ConditionalOnProperty(name = "harry.security.social.open", havingValue = "true")
public class WeixinAutoConfig implements SocialConfigurer {

    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        SocialProperties.WeixinProperties weixinConfig = securityProperties.getSocial().getWeixin();
        WeixinConnectionFactory connectionFactory = new WeixinConnectionFactory(weixinConfig.getProviderId(), weixinConfig.getAppId(),
                weixinConfig.getAppSecret());
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

    @Bean({"connect/weixinConnected", "connect/weixinConnect"})
    @ConditionalOnMissingBean(name = "weixinConnectedView")
    public View weixinConnectedView() {
        return new CustomerConnectView();
    }
}
