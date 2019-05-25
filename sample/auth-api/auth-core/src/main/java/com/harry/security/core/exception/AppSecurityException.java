package com.harry.security.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author harry
 * @version 1.0
 * @title: AppSecurityException
 * @description: TODO
 * @date 2019/5/24 20:58
 */
public class AppSecurityException extends AuthenticationException {

    public AppSecurityException(String msg, Throwable t) {
        super(msg, t);
    }

    public AppSecurityException(String msg) {
        super(msg);
    }
}
