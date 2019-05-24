package com.harry.security.core.authroize.manager;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author harry
 * @version 1.0
 * @title: AuthorizeConfigManager
 * @description: 授权配置管理器
 * @date 2019/5/24 15:30
 */
public interface AuthorizeConfigManager {

    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
