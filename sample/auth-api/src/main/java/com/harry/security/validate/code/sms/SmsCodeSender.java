package com.harry.security.validate.code.sms;

/**
 * @author harry
 * @version 1.0
 * @title: SmsCodeSender
 * @description: 短信验证码发送接口
 * @date 2019/5/11 16:03
 */
public interface SmsCodeSender {

    /**
     * 发送验证码短信
     * @param mobile 接收验证码的手机号
     * @param code 验证码
     */
    void send(String mobile,String code);

}
