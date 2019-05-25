package com.harry.security.app.social;

import com.harry.security.core.constant.SecurityConstants;
import com.harry.security.core.social.BaseSocialConfigurer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author harry
 * @version 1.0
 * @title: AppSocialBeanPostProcessor
 * @description: 所有bean初始化前后都会经过这个类的方法
 * @date 2019/5/24 21:14
 */
public class AppSocialBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (StringUtils.equals(beanName, SecurityConstants.BASE_SOCIAL_CONFIGURER)) {
            BaseSocialConfigurer configurer = (BaseSocialConfigurer) bean;
            //三方用户openid不存在于user_connection表时，重定向的路径
            configurer.signupUrl(SecurityConstants.DEFAULT_APP_SOCIAL_INFO);
            return configurer;
        }
        return bean;
    }
}
