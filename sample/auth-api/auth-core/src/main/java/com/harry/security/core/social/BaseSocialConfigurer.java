package com.harry.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author harry
 * @version 1.0
 * @title: BaseSocialConfigurer
 * @description: 自定义实现SpringSocialConfigurer，更改其标准的过滤器处理路径，使其可配置化
 * @date 2019/5/12 19:56
 */
public class BaseSocialConfigurer extends SpringSocialConfigurer {

    private final String filterProcessUrl;
    private final SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    public BaseSocialConfigurer(String filterProcessUrl, SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor) {
        this.filterProcessUrl = filterProcessUrl;
        this.socialAuthenticationFilterPostProcessor = socialAuthenticationFilterPostProcessor;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessUrl);

        if (socialAuthenticationFilterPostProcessor != null) {
            socialAuthenticationFilterPostProcessor.process(filter);
        }
        return (T) filter;
    }


}
