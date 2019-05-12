package com.harry.security.validate.code.image;

import com.harry.security.validate.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author harry
 * @version 1.0
 * @title: VerifyCodeUtil
 * @description: 图片验证码
 * @date 2019/5/11 14:13
 */
public class ImageCode extends ValidateCode {

    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expireTn) {
        super(code, expireTn);
        this.image=image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image=image;
    }

    public BufferedImage getImage() {
        return image;
    }
}
