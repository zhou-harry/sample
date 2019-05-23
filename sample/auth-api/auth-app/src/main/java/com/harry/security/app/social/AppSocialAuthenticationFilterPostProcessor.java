package com.harry.security.app.social;

import com.harry.security.core.social.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author harry
 * @version 1.0
 * @title: AppSocialAuthenticationFilterPostProcessor
 * @description: App Social认证成功后需要指定成功/失败处理器,Web端则直接跳转至指定页面
 * @date 2019/5/23 22:36
 */
public class AppSocialAuthenticationFilterPostProcessor implements SocialAuthenticationFilterPostProcessor {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    public void process(SocialAuthenticationFilter socialAuthenticationFilter) {
        socialAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
    }

}
