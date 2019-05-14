package com.harry.security.validate.code.sms;

import com.harry.security.constant.ValidateCodeTypeEnum;
import com.harry.security.properties.SecurityProperties;
import com.harry.security.validate.code.ValidateCode;
import com.harry.security.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author harry
 * @version 1.0
 * @title: SmsCodeGenerator
 * @description: 短信验证码生成类
 * @date 2019/5/11 15:48
 */
@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCodeTypeEnum getValidateCodeType() {
        return ValidateCodeTypeEnum.SMS;
    }

    @Override
    public ValidateCode generator(ServletWebRequest request) {
        //生成验证码，长度从配置读取
        String code = RandomStringUtils.randomNumeric(securityProperties.getValidateCode().getSms().getLength());

        return new SmsCode(code, securityProperties.getValidateCode().getSms().getExpireIn());
    }

}
