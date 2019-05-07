package com.harry.security.util;

public class SecurityCode {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    public final static String LOGIN_PAGE = "/auth/require";

    public final static String LOGIN_PROCESSINGURL="/auth/form";

    public final static String[] MATCHERS ={
            SecurityCode.LOGIN_PAGE,
            "/code/image",
            "/error"
    };

}
