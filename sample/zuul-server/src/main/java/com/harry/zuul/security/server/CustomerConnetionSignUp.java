package com.harry.zuul.security.server;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * @author harry
 * @version 1.0
 * @title: CustomerConnetionSignUp
 * @description:
 * 第三方授权完成后系统默认通过execute()返回的内容默认与第三方认证账号进行关联并存入系统
 * 如果系统中没有ConnectionSignUp的实现对象，则交给注册页面进行处理
 * @date 2019/5/12 21:31
 */
@Component
public class CustomerConnetionSignUp implements ConnectionSignUp {

    @Override
    public String execute(Connection<?> connection) {
        // 根据社交用户信息默认创建用户并返回用户业务唯一标识
        return connection.getDisplayName();
    }

}
