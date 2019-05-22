package com.harry.security.core.controller;

import com.harry.security.core.constant.SecurityConstants;
import com.harry.security.core.validate.code.image.ImageCodeProcessor;
import com.harry.security.core.validate.code.sms.SmsCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码Controller
 */
@RestController
public class ValidateCodeController {

    //图片验证码生成器
    @Autowired
    private ImageCodeProcessor imageCodeProcessor;
    //短信验证码生成器
    @Autowired
    private SmsCodeProcessor smsCodeProcessor;


    /**
     * 图片验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

        imageCodeProcessor.process(new ServletWebRequest(request, response));

    }

    /**
     * 短信验证码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

        smsCodeProcessor.process(new ServletWebRequest(request, response));

    }

}
