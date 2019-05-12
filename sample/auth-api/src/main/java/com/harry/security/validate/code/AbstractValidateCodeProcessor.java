package com.harry.security.validate.code;

import com.harry.security.constant.ValidateCodeTypeEnum;
import com.harry.security.exception.ValidateCodeException;
import com.harry.security.validate.code.image.ImageCode;
import com.harry.security.validate.code.sms.SmsCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author harry
 * @version 1.0
 * @title: AbstractValidateCodeProcessor
 * @description: TODO
 * @date 2019/5/11 22:44
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode,G extends ValidateCodeGenerator> implements ValidateCodeProcessor {

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void process(ServletWebRequest request) throws Exception {
        //生成验证码
        C validateCode = generate(request);
        System.out.println("验证码："+validateCode.getCode());
        //持久化
        save(request, validateCode);
        //发送验证码
        send(request, validateCode);
    }

    private void save(ServletWebRequest request, C validateCode) {
        ValidateCode codeToSave = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        validateCodeRepository.save(request, codeToSave, getValidateCodeType(validateCode));
    }

    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) throws Exception {
        return (C) getValidateCodeGenerator().generator(request);
    }

    private ValidateCodeTypeEnum getValidateCodeType(ValidateCode validateCode) {
        if (validateCode instanceof ImageCode) {
            return ValidateCodeTypeEnum.IMAGE;
        } else if (validateCode instanceof SmsCode) {
            return ValidateCodeTypeEnum.SMS;
        } else {
            throw new ValidateCodeException("不支持的validateCode类型" + validateCode.getClass().getCanonicalName());
        }
    }

    protected abstract G getValidateCodeGenerator();

    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

}
