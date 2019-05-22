package com.harry.security.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码校验异常
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
