package com.harry.security.core.constant;

public class SecurityConstants {

    /**
     * 默认登录页面url
     */
    public static final String DEFAULT_SIGNIN_PAGE_URL = "/signIn.html";
    /**
     * 默认注册页面url
     */
    public static final String DEFAULT_SIGNUP_PAGE_URL = "/signUp.html";
    /**
     * 默认退出页面url
     */
    public static final String DEFAULT_SIGNOUT_PAGE_URL = "/signOut.html";
    /**
     * App端默认注册跳转路径
     */
    public static final String DEFAULT_APP_SOCIAL_INFO = "/social/signup";
    /**
     * Web端第三方注册绑定信息
     */
    public static final String DEFAULT_WEB_SOCIAL_INFO = "/info";
    /**
     * 默认的用户名密码登录请求处理url
     */
    public final static String DEFAULT_SIGNIN_PROCESS_URL_FORM = "/auth/form";
    /**
     * 默认的手机验证码登录请求处理url
     */
    public final static String DEFAULT_SIGNIN_PROCESS_URL_MOBILE = "/auth/mobile";
    /**
     * 默认的openid登陆请求处理url
     */
    public static final String DEFAULT_SIGNIN_PROCESS_URL_OPENID = "/auth/openId";
    /**
     * 默认记住账号密码时间
     */
    public static final int DEFAULT_REMEMBERME_SECONDS = 3600;
    /**
     * 默认的验证码请求的前缀
     */
    public static final String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/verifyCode";

    public static final String SESSION_KEY_IMAGE = "SESSION_KEY_IMAGE_CODE";
    public static final String SESSION_KEY_SMS = "SESSION_KEY_SMS_CODE";
    /**
     * 默认表单身份认证地址
     */
    public final static String DEFAULT_LOGIN_PAGE = "/auth/require";
    /**
     * 默认退出url
     */
    public final static String DEFAULT_LOGOUT_PAGE = "/signOut";
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
    public static final String DEFAULT_FILTER_PROCESSES_URL = "/auth";

    /**
     * 图片验证码处理器实例名
     */
    public static final String DEFAULT_IMAGE_CODE_PROCESSOR = "imageCodeProcessor";

    /**
     * 短信验证码处理器实例名
     */
    public static final String DEFAULT_SMS_CODE_PROCESSOR = "smsCodeProcessor";
    /**
     * session失效时跳转的地址
     */
    public static final String DEFAULT_SESSION_INVALID_URL = "/session/invalid.html";

    /**
     * 达到最大session时是否阻止新的登录请求，默认为false，不阻止，新的登录会将老的登录失效掉
     */
    public static final boolean DEFAULT_MAX_SESSIONS_PREVENTS_LOGIN = false;
    /**
     * 同一个用户在系统中的最大session数，默认1
     */
    public static final int DEFAULT_MAXIMUM_SESSIONS = 1;

    /**
     * 社交登陆时， 传递的openId参数名
     */
    public static final String DEFAULT_REQUEST_PARAMETER_OPENID = "openId";
    /**
     * 社交登陆时，传递的providerId参数名
     */
    public static final String DEFAULT_REQUEST_PARAMETER_PROVIDERID = "providerId";

    /**
     * 默认OAuth2签名
     */
    public static final String DEFAULT_OAUTH2_SIGNING_KEY = "harry";
    /**
     * 默认OAuth2 Token存储方式(jwt/redis)
     */
    public static final String DEFAULT_OAUTH2_STORETYPE = "jwt";
    /**
     * 自定义实现SpringSocialConfigurer名称
     */
    public static final String BASE_SOCIAL_CONFIGURER = "baseSocialConfigurer";
    /**
     * SSO 授权认证请求地址
     */
    public static final String BASE_OAUTH2_AUTHORIZE = "/oauth/authorize";
    /**
     * SSO登录默认地址
     */
    public static final String BASE_SSO_LOGIN = "/login";

    public static final String BASE_ERROR = "/error";


    public final static String[] MATCHERS = {
            BASE_ERROR,
            BASE_SSO_LOGIN,
            DEFAULT_LOGIN_PAGE,
            DEFAULT_SIGNIN_PROCESS_URL_MOBILE,
            DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
            BASE_OAUTH2_AUTHORIZE
    };
}
