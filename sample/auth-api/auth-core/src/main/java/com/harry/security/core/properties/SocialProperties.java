package com.harry.security.core.properties;

import com.harry.security.core.constant.SecurityConstants;

/**
 * @author harry
 * @version 1.0
 * @title: SocialProperties
 * @description: 社交认证配置属性
 * @date 2019/5/12 16:37
 */
public class SocialProperties {

    private String filterProcessUrl = SecurityConstants.DEFAULT_FILTER_PROCESSES_URL;

    private boolean open = false;//是否启用社交认证

    private QQProperties qq = new QQProperties();

    private WeixinProperties weixin = new WeixinProperties();

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public WeixinProperties getWeixin() {
        return weixin;
    }

    public void setWeixin(WeixinProperties weixin) {
        this.weixin = weixin;
    }

    public String getFilterProcessUrl() {
        return filterProcessUrl;
    }

    public void setFilterProcessUrl(String filterProcessUrl) {
        this.filterProcessUrl = filterProcessUrl;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public class QQProperties {

        /**
         * Application id.
         */
        private String appId;

        /**
         * Application secret.
         */
        private String appSecret;

        private String providerId = "qq";

        public String getAppId() {
            return this.appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppSecret() {
            return this.appSecret;
        }

        public void setAppSecret(String appSecret) {
            this.appSecret = appSecret;
        }

        public String getProviderId() {
            return providerId;
        }

        public void setProviderId(String providerId) {
            this.providerId = providerId;
        }
    }

    public class WeixinProperties {

        /**
         * Application id.
         */
        private String appId;

        /**
         * Application secret.
         */
        private String appSecret;

        private String providerId = "weixin";

        public String getAppId() {
            return this.appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppSecret() {
            return this.appSecret;
        }

        public void setAppSecret(String appSecret) {
            this.appSecret = appSecret;
        }

        public String getProviderId() {
            return providerId;
        }

        public void setProviderId(String providerId) {
            this.providerId = providerId;
        }
    }
}
