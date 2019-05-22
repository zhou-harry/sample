package com.harry.security.core.validate.code.sms;

import com.harry.security.core.validate.code.ValidateCode;

import java.time.LocalDateTime;

/**
 * @author harry
 * @version 1.0
 * @title: SmsCode
 * @description: TODO
 * @date 2019/5/11 22:54
 */
public class SmsCode extends ValidateCode {

    private static final long serialVersionUID = 1L;

    public SmsCode(String code, int expireIn) {
        super(code, expireIn);
    }

    public SmsCode(String code, LocalDateTime expireTime) {
        super(code, expireTime);
    }

    @Override
    public String toString() {
        return "SmsCode [code=" + code + ", expireTime=" + expireTime + "]";
    }
}
