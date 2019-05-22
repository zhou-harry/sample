package com.harry.security.core.validate.code.image;

import com.harry.security.core.constant.SecurityConstants;
import com.harry.security.core.validate.code.AbstractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * @author harry
 * @version 1.0
 * @title: ImageCodeProcessor
 * @description: 图片验证码处理器
 * @date 2019/5/11 23:08
 */
@Component(SecurityConstants.DEFAULT_IMAGE_CODE_PROCESSOR)
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode, ImageCodeGenerator> {

    @Autowired
    private ImageCodeGenerator imageCodeGenerator;

    @Override
    protected ImageCodeGenerator getValidateCodeGenerator() {
        return imageCodeGenerator;
    }

    @Override
    protected void send(ServletWebRequest request, ImageCode validateCode) throws Exception {
        request.getResponse().addHeader("Content-Type", "image/jpeg");
        ImageIO.write(
                validateCode.getImage(),
                "JPEG",
                request.getResponse().getOutputStream()
        );
    }
}
