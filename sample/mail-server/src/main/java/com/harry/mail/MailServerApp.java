package com.harry.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @author harry
 * @version 1.0
 * @title: com.harry.mail.MailApp
 * @description: 邮件服务启动器
 * @date 2019/5/4 13:12
 */
@SpringBootApplication
@EnableDiscoveryClient // 激活服务发现客户端
@EnableHystrix // 激活服务短路
public class MailServerApp {

    public static void main(String[] args) {
        SpringApplication.run(MailServerApp.class, args);
    }
}
