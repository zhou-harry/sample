package com.harry.security.core.validate.code;

import com.harry.security.core.constant.ValidateCodeTypeEnum;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成接口
 * @author harry
 * @version 1.0
 * @title: ValidateCodeGenerator
 * @description: 所有验证码实现的父接口
 * @date 2019/5/11 13:59
 */
public interface ValidateCodeGenerator {

    /**
     * 获取验证码生成器对应的验证码类型
     * @return
     */
    ValidateCodeTypeEnum getValidateCodeType();

    /**
     * 生成验证码
     * @param request
     * @return
     */
    ValidateCode generator(ServletWebRequest request);

}
