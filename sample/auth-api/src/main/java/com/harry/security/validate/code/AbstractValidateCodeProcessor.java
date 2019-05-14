package com.harry.security.validate.code;

import com.harry.security.constant.ValidateCodeTypeEnum;
import com.harry.security.exception.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author harry
 * @version 1.0
 * @title: AbstractValidateCodeProcessor
 * @description: TODO
 * @date 2019/5/11 22:44
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode, G extends ValidateCodeGenerator> implements ValidateCodeProcessor {

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void process(ServletWebRequest request) throws Exception {
        //生成验证码
        C validateCode = generate(request);
        System.out.println("验证码：" + validateCode.getCode());
        //持久化
        save(request, validateCode);
        //发送验证码
        send(request, validateCode);
    }

    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeTypeEnum validateCodeType = this.getValidateCodeType();

        ValidateCode codeInRepository = validateCodeRepository.get(request, validateCodeType);

        if (codeInRepository == null) {
            throw new ValidateCodeException(String.format("需要%s验证码", validateCodeType));
        }

        //从请求里，拿到code[来源于表单]
        String codeInRequest = request.getParameter(validateCodeType.getParameterNameOnValidate());
        // 验证码不能为空
        if (StringUtils.isEmpty(codeInRequest)) {
            throw new ValidateCodeException("请填写验证码");
        }

        // 验证码不存在
        if (codeInRequest == null) {
            throw new ValidateCodeException("请先获取验证码");
        }

        // 验证码已过期
        if (codeInRepository.isExpired()) {
            validateCodeRepository.remove(request, validateCodeType);
            throw new ValidateCodeException("验证码已过期");
        }

        // 验证码不匹配
        if (!StringUtils.equalsIgnoreCase(codeInRepository.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
        //验证完成后删除此验证码
        validateCodeRepository.remove(request, validateCodeType);
    }

    private void save(ServletWebRequest request, C validateCode) {
        ValidateCode codeToSave = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        validateCodeRepository.save(request, codeToSave, getValidateCodeType());
    }

    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) throws Exception {
        return (C) getValidateCodeGenerator().generator(request);
    }

    private ValidateCodeTypeEnum getValidateCodeType() {

        G validateCodeGenerator = this.getValidateCodeGenerator();

        ValidateCodeTypeEnum validateCodeType = validateCodeGenerator.getValidateCodeType();
        if (validateCodeType == null) {
            throw new ValidateCodeException(validateCodeGenerator.getClass().getCanonicalName()+" 未指定验证码类型");
        }
        return validateCodeType;
    }

    protected abstract G getValidateCodeGenerator();

    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

}
