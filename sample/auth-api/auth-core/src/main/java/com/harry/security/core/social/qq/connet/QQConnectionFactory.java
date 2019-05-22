package com.harry.security.core.social.qq.connet;

import com.harry.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author harry
 * @version 1.0
 * @title: QQConnectionFactory
 * @description: 产生connection，然后connection会被封装成SocialAuthenticationToke
 * @date 2019/5/12 15:54
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    public QQConnectionFactory(String providerId,String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId,appSecret), new QQAdapter());
    }

}
