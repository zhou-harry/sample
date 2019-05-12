package com.harry.security.constant;

public class SecurityConstants {

    /**
     * 默认登录页面url
     */
    public static final String DEFAULT_SIGNIN_PAGE_URL = "/loginForm.html";
    /**
     * 默认注册页面url
     */
    public static final String DEFAULT_SIGNUP_PAGE_URL = "/signUp.html";

    /**
     * 默认记住账号密码时间
     */
    public static final int DEFAULT_REMEMBERME_SECONDS=3600;

    /**
     * 默认的验证码请求的前缀
     */
    public static final String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/verifyCode";

    public static final String SESSION_KEY_IMAGE = "SESSION_KEY_IMAGE_CODE";
    public static final String SESSION_KEY_SMS = "SESSION_KEY_SMS_CODE";

    public final static String LOGIN_PAGE = "/auth/require";

    /**
     * 默认的用户名密码登录请求处理url
     */
    public final static String DEFAULT_SIGNIN_PROCESS_URL_FORM = "/auth/form";

    /**
     * 默认的手机验证码登录请求处理url
     */
    public final static String DEFAULT_SIGNIN_PROCESS_URL_MOBILE = "/auth/mobile";

    public final static String[] MATCHERS = {
            LOGIN_PAGE,
            DEFAULT_SIGNIN_PROCESS_URL_MOBILE,
            DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
            "/error"
    };

    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
    /**
     * 默认的Social UserConnection表名的前缀
     */
    public static final String DEFAULT_SOCIAL_USER_CONNECTION_PREFIX = "t_";
    /**
     * SocialAuthenticationFilter默认的处理路径
     */
    public static final String DEFAULT_FILTER_PROCESSES_URL="/auth";

}
