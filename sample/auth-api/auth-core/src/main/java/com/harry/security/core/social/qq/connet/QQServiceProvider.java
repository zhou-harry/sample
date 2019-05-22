package com.harry.security.core.social.qq.connet;

import com.harry.security.core.social.qq.api.QQ;
import com.harry.security.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * 1. 使用OAuth2Template执行流程
 * 2. 使用QQImple获取三方用户数据
 *
 * @author harry
 * @version 1.0
 * @title: QQServiceProvider
 * @description: TODO
 * @date 2019/5/12 15:38
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    //获取QQ授权码地址
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    //获取QQ Token地址
    private static final String URL_ACCESSTOKEN = "https://graph.qq.com/oauth2.0/token";

    private String appId;

    public QQServiceProvider(String appId, String appSecret) {
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESSTOKEN));
        this.appId=appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}
