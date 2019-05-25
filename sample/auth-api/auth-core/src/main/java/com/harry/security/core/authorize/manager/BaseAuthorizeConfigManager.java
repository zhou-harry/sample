package com.harry.security.core.authorize.manager;

import com.harry.security.core.authorize.provider.AuthorizeConfigProvider;
import com.harry.security.core.authorize.server.AuthorizeServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author harry
 * @version 1.0
 * @title: BaseAuthorizeConfigManager
 * @description: 授权配置管理器基本实现
 * @date 2019/5/24 15:31
 */
@Component
public class BaseAuthorizeConfigManager implements AuthorizeConfigManager {

    @Autowired
    private List<AuthorizeConfigProvider> authorizeConfigProviders;
    @Autowired(required = false)
    private AuthorizeServer authorizeServer;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

        for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders) {
            authorizeConfigProvider.config(config);
        }

        if (authorizeServer == null) {
            // 其它所有请求需要身份验证
            config.anyRequest().authenticated();
        } else {
            config.anyRequest().access("@authorizeServer.hasPermission(request, authentication)");
        }

    }
}
