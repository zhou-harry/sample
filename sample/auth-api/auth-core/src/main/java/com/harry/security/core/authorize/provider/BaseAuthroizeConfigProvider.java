package com.harry.security.core.authorize.provider;

import com.harry.security.core.constant.SecurityConstants;
import com.harry.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author harry
 * @version 1.0
 * @title: BaseAuthroizeConfigProvider
 * @description: 基础授权配置
 * @date 2019/5/24 15:13
 */
@Component
@Order(Integer.MAX_VALUE - 100)
public class BaseAuthroizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        //系统中需要放行的请求
        config
                .antMatchers(SecurityConstants.MATCHERS).permitAll()
                .antMatchers(securityProperties.getBrowser().getPermitUrl()).permitAll()
                .antMatchers(
                        securityProperties.getBrowser().getSignInUrl(),
                        securityProperties.getBrowser().getSignUpUrl(),
                        securityProperties.getBrowser().getSignOutUrl(),
                        securityProperties.getBrowser().getSigninProcessUrlForm(),
                        securityProperties.getBrowser().getSigninProcessUrlMobile(),
                        securityProperties.getBrowser().getSigninProcessUrlOpenId(),
                        securityProperties.getBrowser().getSigninProcessUrlMobile(),
                        securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
                        securityProperties.getBrowser().getLoginPage(),
                        securityProperties.getBrowser().getLogoutPage()
                ).permitAll()
        ;
    }
}