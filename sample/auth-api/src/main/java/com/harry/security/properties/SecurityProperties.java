package com.harry.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

//读取配置文件内的信息
@ConfigurationProperties(prefix="harry.security")
@Getter
@Setter
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();
    private ValidateCodeProperties validateCode=new ValidateCodeProperties();

    public class BrowserProperties {
        //标准的登录页面，如果其他项目没有配置则使用默认的登录配置
        private String loginPage = "/loginForm.html";

        private LoginType loginType = LoginType.JSON;//默认返回json

        private int rememberMeSeconds=3600;

        public LoginType getLoginType() {
            return loginType;
        }

        public void setLoginType(LoginType loginType) {
            this.loginType = loginType;
        }

        public String getLoginPage() {
            return loginPage;
        }
        public void setLoginPage(String loginPage) {
            this.loginPage = loginPage;
        }

        public int getRememberMeSeconds() {
            return rememberMeSeconds;
        }

        public void setRememberMeSeconds(int rememberMeSeconds) {
            this.rememberMeSeconds = rememberMeSeconds;
        }
    }

    public class ValidateCodeProperties {

        private ImageCodeProperties image = new ImageCodeProperties();

        public ImageCodeProperties getImage() {
            return image;
        }

        public void setImage(ImageCodeProperties image) {
            this.image = image;
        }

        /**
         * 生成二维码默认配置
         */
        public class ImageCodeProperties {

            private int width = 100;    //图片长度
            private int height = 40;   //图片高度
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

    }

}
