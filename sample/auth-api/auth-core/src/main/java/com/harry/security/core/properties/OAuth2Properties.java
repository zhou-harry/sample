package com.harry.security.core.properties;

import com.harry.security.core.constant.SecurityConstants;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;

/**
 * @author harry
 * @version 1.0
 * @title: OAuth2Properties
 * @description: OAuth2属性配置
 * @date 2019/5/23 17:44
 */
public class OAuth2Properties {

    private String signingKey = SecurityConstants.DEFAULT_OAUTH2_SIGNING_KEY;

    private String storeType=SecurityConstants.DEFAULT_OAUTH2_STORETYPE;

    private OAuth2ClientProperties[] clients = {};

    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }
}
