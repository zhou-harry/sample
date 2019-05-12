package com.harry.security.validate.code.image;

import com.harry.security.properties.SecurityProperties;
import com.harry.security.util.VerifyCodeUtil;
import com.harry.security.validate.code.ValidateCode;
import com.harry.security.validate.code.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.image.BufferedImage;

/**
 * @author harry
 * @version 1.0
 * @title: ImageCodeGenerator
 * @description: 图片验证码生成类
 * @date 2019/5/11 14:09
 */
@Component("imageCodeGenerator")
public class ImageCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generator(ServletWebRequest request) {

        //先从request里读取有没有长、宽、字符个数参数，有的话就用，没有用默认的
        int width  = ServletRequestUtils.getIntParameter(request.getRequest(), "width",securityProperties.getValidateCode().getImage().getWidth());

        int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height",securityProperties.getValidateCode().getImage().getHeight());

        int verifySize = this.securityProperties.getValidateCode().getImage().getLength();

        VerifyCodeUtil codeUtil = new VerifyCodeUtil(width, height, verifySize);

        BufferedImage image = codeUtil.getImage();

        return new ImageCode(
                image,
                codeUtil.getVerifyCode(),
                this.securityProperties.getValidateCode().getImage().getExpireIn()
        );
    }

}
