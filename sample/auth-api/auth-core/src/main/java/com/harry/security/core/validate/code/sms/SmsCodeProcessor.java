package com.harry.security.core.validate.code.sms;

import com.harry.security.core.constant.SecurityConstants;
import com.harry.security.core.validate.code.AbstractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author harry
 * @version 1.0
 * @title: SmsCodeProcessor
 * @description: 短信验证码处理器
 * @date 2019/5/11 22:59
 */
@Component(SecurityConstants.DEFAULT_SMS_CODE_PROCESSOR)
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<SmsCode, SmsCodeGenerator> {

    @Autowired
    private SmsCodeGenerator smsCodeGenerator;

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected SmsCodeGenerator getValidateCodeGenerator() {
        return smsCodeGenerator;
    }

    @Override
    protected void send(ServletWebRequest request, SmsCode validateCode) throws Exception {
        //获取手机号
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
        //发送短信验证码
        smsCodeSender.send(mobile, validateCode.getCode());
    }
}
