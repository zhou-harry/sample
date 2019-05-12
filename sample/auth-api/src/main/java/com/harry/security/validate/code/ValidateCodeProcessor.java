package com.harry.security.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author harry
 * @version 1.0
 * @title: ValidateCodeProcessor
 * @description: TODO
 * @date 2019/5/11 22:45
 */
public interface ValidateCodeProcessor {

    /**
     * 处理
     * @param request
     * @throws Exception
     */
    void process(ServletWebRequest request) throws Exception;

}
