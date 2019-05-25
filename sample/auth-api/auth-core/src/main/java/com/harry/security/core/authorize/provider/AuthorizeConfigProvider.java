package com.harry.security.core.authorize.provider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author harry
 * @version 1.0
 * @title: AuthorizeConfigProvider
 * @description: 授权配置Provider
 * @date 2019/5/24 15:17
 */
public interface AuthorizeConfigProvider {

    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
