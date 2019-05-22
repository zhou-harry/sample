package com.harry.security.core.social.weixin.connet;

import com.harry.security.core.social.weixin.api.Weixin;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * @author harry
 * @version 1.0
 * @title: WeixinConnectionFactory
 * @description: 微信连接工厂
 * @date 2019/5/12 18:42
 */
public class WeixinConnectionFactory extends OAuth2ConnectionFactory<Weixin> {


    /**
     * @param appId
     * @param appSecret
     */
    public WeixinConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new WeixinServiceProvider(appId, appSecret), new WeixinAdapter());
    }

    /**
     * 由于微信的openId是和accessToken一起返回的，所以在这里直接根据accessToken设置providerUserId即可，不用像QQ那样通过QQAdapter来获取
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if (accessGrant instanceof WeixinAccessGrant) {
            return ((WeixinAccessGrant) accessGrant).getOpenId();
        }
        return null;
    }

    /*
     * 微信 Adaper是多实例的
     * (non-Javadoc)
     * @see org.springframework.social.connect.support.OAuth2ConnectionFactory#createConnection(org.springframework.social.oauth2.AccessGrant)
     */
    public Connection<Weixin> createConnection(AccessGrant accessGrant) {

        String openId = extractProviderUserId(accessGrant);
        return new OAuth2Connection<Weixin>(
                getProviderId(),
                openId,
                accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(),
                accessGrant.getExpireTime(),
                getOAuth2ServiceProvider(),
                getApiAdapter(openId)
        );
    }

    /* (non-Javadoc)
     * 微信 Adaper是多实例的
     * @see org.springframework.social.connect.support.OAuth2ConnectionFactory#createConnection(org.springframework.social.connect.ConnectionData)
     */
    public Connection<Weixin> createConnection(ConnectionData data) {
        return new OAuth2Connection<Weixin>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
    }

    private ApiAdapter<Weixin> getApiAdapter(String providerUserId) {
        return new WeixinAdapter(providerUserId);
    }

    private OAuth2ServiceProvider<Weixin> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<Weixin>) getServiceProvider();
    }

}
