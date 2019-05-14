package com.harry.security.properties;

import com.harry.security.constant.SecurityConstants;

/**
 * @author harry
 * @version 1.0
 * @title: SessionProperties
 * @description: Session 外部化配置属性
 * @date 2019/5/14 23:46
 */
public class SessionProperties {

    /**
     * 同一个用户在系统中的最大session数，默认1
     */
    private int maximumSessions = SecurityConstants.DEFAULT_MAXIMUM_SESSIONS;
    /**
     * 达到最大session时是否阻止新的登录请求，默认为false，不阻止，新的登录会将老的登录失效掉
     */
    private boolean maxSessionsPreventsLogin=SecurityConstants.DEFAULT_MAX_SESSIONS_PREVENTS_LOGIN;
    /**
     * session失效时跳转的地址
     */
    private String sessionInvalidUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;

    public int getMaximumSessions() {
        return maximumSessions;
    }

    public void setMaximumSessions(int maximumSessions) {
        this.maximumSessions = maximumSessions;
    }

    public boolean isMaxSessionsPreventsLogin() {
        return maxSessionsPreventsLogin;
    }

    public void setMaxSessionsPreventsLogin(boolean maxSessionsPreventsLogin) {
        this.maxSessionsPreventsLogin = maxSessionsPreventsLogin;
    }

    public String getSessionInvalidUrl() {
        return sessionInvalidUrl;
    }

    public void setSessionInvalidUrl(String sessionInvalidUrl) {
        this.sessionInvalidUrl = sessionInvalidUrl;
    }
}
