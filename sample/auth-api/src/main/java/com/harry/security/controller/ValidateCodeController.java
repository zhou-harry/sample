package com.harry.security.controller;

import com.harry.security.properties.SecurityProperties;
import com.harry.security.util.ImageCode;
import com.harry.security.util.SecurityCode;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ValidateCodeController {

    //操作Session的类
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private final SecurityProperties securityProperties;

    public ValidateCodeController(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.根据随机数生成数字
        SecurityProperties.ValidateCodeProperties.ImageCodeProperties image = securityProperties.getValidateCode().getImage();
        ImageCode imageCode = ImageCode.build(new ServletWebRequest(request),image);
        System.out.println("验证码:"+imageCode.getCode());
        //2.将随机数存到Session中
        //把请求传递进ServletWebRequest,
        sessionStrategy.setAttribute(new ServletWebRequest(request), SecurityCode.SESSION_KEY, imageCode);
        //3.将生成的图片写到接口的响应中
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());

    }

}
