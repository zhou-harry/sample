package com.harry.security.properties;

import com.harry.security.constant.AuthenticationResponseTypeEnum;
import com.harry.security.constant.SecurityConstants;

/**
 * @author harry
 * @version 1.0
 * @title: BrowserProperties
 * @description: 表单属性配置
 * @date 2019/5/11 23:45
 */
public class BrowserProperties {

    //表单身份认证地址
    private String loginPage=SecurityConstants.DEFAULT_LOGIN_PAGE;
    //账号退出地址
    private String logoutPage=SecurityConstants.DEFAULT_LOGOUT_PAGE;
    //用户名密码登录请求处理url
    private String signinProcessUrlForm = SecurityConstants.DEFAULT_SIGNIN_PROCESS_URL_FORM;
    //手机验证码登录请求处理url
    private String signinProcessUrlMobile = SecurityConstants.DEFAULT_SIGNIN_PROCESS_URL_MOBILE;
    //标准的登录页面，如果其他项目没有配置则使用默认的登录配置
    private String signInUrl = SecurityConstants.DEFAULT_SIGNIN_PAGE_URL;
    //注册页面
    private String signUpUrl = SecurityConstants.DEFAULT_SIGNUP_PAGE_URL;
    //退出页面
    private String signOutUrl;
    //登陆请求方式
    private AuthenticationResponseTypeEnum loginType = AuthenticationResponseTypeEnum.JSON;
    //默认记住账号密码时间(秒)
    private int rememberMeSeconds = SecurityConstants.DEFAULT_REMEMBERME_SECONDS;


    private SessionProperties session=new SessionProperties();


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

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getLogoutPage() {
        return logoutPage;
    }

    public void setLogoutPage(String logoutPage) {
        this.logoutPage = logoutPage;
    }

    public String getSignOutUrl() {
        return signOutUrl;
    }

    public void setSignOutUrl(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }
}
