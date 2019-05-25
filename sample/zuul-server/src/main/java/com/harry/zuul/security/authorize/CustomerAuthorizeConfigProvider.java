package com.harry.zuul.security.authorize;

import com.harry.security.core.authorize.provider.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author harry
 * @version 1.0
 * @title: CustomerAuthorizeConfigProvider
 * @description: TODO
 * @date 2019/5/25 17:33
 */
@Component
@Order(Integer.MAX_VALUE)//需要最后调用，不然后面的配置会失效
public class CustomerAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        // anyRequest()需要最后调用，不然后面的配置会失效
        config.anyRequest().access("@authorizeServer.hasPermission(request, authentication)");
    }

}
