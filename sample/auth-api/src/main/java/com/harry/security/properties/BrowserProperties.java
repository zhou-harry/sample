package com.harry.security.properties;

import com.harry.security.constant.AuthenticationResponseTypeEnum;
import com.harry.security.constant.SecurityConstants;

/**
 * @author harry
 * @version 1.0
 * @title: BrowserProperties
 * @description: TODO
 * @date 2019/5/11 23:45
 */
public class BrowserProperties {

    //标准的登录页面，如果其他项目没有配置则使用默认的登录配置
    private String signInUrl = SecurityConstants.DEFAULT_SIGNIN_PAGE_URL;
    private String signUpUrl = SecurityConstants.DEFAULT_SIGNUP_PAGE_URL;

    private AuthenticationResponseTypeEnum loginType = AuthenticationResponseTypeEnum.JSON;

    private int rememberMeSeconds = SecurityConstants.DEFAULT_REMEMBERME_SECONDS;

    private String signinProcessUrlForm = SecurityConstants.DEFAULT_SIGNIN_PROCESS_URL_FORM;
    private String signinProcessUrlMobile = SecurityConstants.DEFAULT_SIGNIN_PROCESS_URL_MOBILE;


    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public AuthenticationResponseTypeEnum getLoginType() {
        return loginType;
    }

    public void setLoginType(AuthenticationResponseTypeEnum loginType) {
        this.loginType = loginType;
    }

    public String getSigninProcessUrlForm() {
        return signinProcessUrlForm;
    }

    public void setSigninProcessUrlForm(String signinProcessUrlForm) {
        this.signinProcessUrlForm = signinProcessUrlForm;
    }

    public String getSigninProcessUrlMobile() {
        return signinProcessUrlMobile;
    }

    public void setSigninProcessUrlMobile(String signinProcessUrlMobile) {
        this.signinProcessUrlMobile = signinProcessUrlMobile;
    }

    public String getSignInUrl() {
        return signInUrl;
    }

    public void setSignInUrl(String signInUrl) {
        this.signInUrl = signInUrl;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }
}
