package com.harry.security.core.properties;

/**
 * @author harry
 * @version 1.0
 * @title: ValidateCodeProperties
 * @description: TODO
 * @date 2019/5/12 0:26
 */
public class ValidateCodeProperties {



    private ImageCodeProperties image = new ImageCodeProperties();

    private SmsCodeProperties sms=new SmsCodeProperties();

    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }

    public SmsCodeProperties getSms() {
        return sms;
    }

    public void setSms(SmsCodeProperties sms) {
        this.sms = sms;
    }

    /**
     * 图片验证码默认配置
     */
    public class ImageCodeProperties {

        private int width = 100;    //图片宽
        private int height = 40;   //图片高
        private int length = 4;    //验证码长度
        private int expireIn = 60; //失效时间
        private String url;        //多个请求需要验证；逗号隔开

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getWidth() {
            return width;
        }
        public void setWidth(int width) {
            this.width = width;
        }
        public int getHeight() {
            return height;
        }
        public void setHeight(int height) {
            this.height = height;
        }
        public int getLength() {
            return length;
        }
        public void setLength(int length) {
            this.length = length;
        }
        public int getExpireIn() {
            return expireIn;
        }
        public void setExpireIn(int expireIn) {
            this.expireIn = expireIn;
        }
    }

    /**
     * 短信验证码默认配置
     */
    public class SmsCodeProperties{

        //验证码字符个数
        private int length = 4;
        //过期时间
        private int expireIn = 60;

        private String url; //拦截的url



        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getExpireIn() {
            return expireIn;
        }

        public void setExpireIn(int expireIn) {
            this.expireIn = expireIn;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
