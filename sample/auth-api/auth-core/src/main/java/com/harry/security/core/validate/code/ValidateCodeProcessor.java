package com.harry.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author harry
 * @version 1.0
 * @title: ValidateCodeProcessor
 * @description: 验证码处理器
 * @date 2019/5/11 22:45
 */
public interface ValidateCodeProcessor {

    /**
     * 生成
     *
     * @param request
     * @throws Exception
     */
    void process(ServletWebRequest request) throws Exception;


    /**
     * 校验
     *
     * @param request
     */
    void validate(ServletWebRequest request);

}
