package com.harry.security.validate.code;

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

    ValidateCode generator(ServletWebRequest request);

}
