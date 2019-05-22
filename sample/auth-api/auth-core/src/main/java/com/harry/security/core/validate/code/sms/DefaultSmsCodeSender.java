package com.harry.security.core.validate.code.sms;

/**
 * @author harry
 * @version 1.0
 * @title: DefaultSmsCodeSender
 * @description: 默认的短信验证码发送类
 * @date 2019/5/11 16:05
 */
public class DefaultSmsCodeSender implements SmsCodeSender{
    @Override
    public void send(String mobile, String code) {
        System.err.println("向手机 :"+mobile+" 发送短信验证码 :"+code);
    }
}
