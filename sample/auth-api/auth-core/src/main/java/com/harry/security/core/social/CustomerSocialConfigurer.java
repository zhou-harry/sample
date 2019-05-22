package com.harry.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author harry
 * @version 1.0
 * @title: CustomerSocialConfigurer
 * @description: 自定义实现SpringSocialConfigurer，更改其标准的过滤器处理路径，使其可配置化
 * @date 2019/5/12 19:56
 */
public class CustomerSocialConfigurer extends SpringSocialConfigurer {

    private final String filterProcessUrl;

    public CustomerSocialConfigurer(String filterProcessUrl) {
        this.filterProcessUrl = filterProcessUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessUrl);
        return (T) filter;
    }


}
