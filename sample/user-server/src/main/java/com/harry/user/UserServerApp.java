package com.harry.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//database-api中存在数据库组件，此处防止autoconfig读取spring数据源配置
@EnableHystrix //激活服务短路
@EnableDiscoveryClient //激活服务发现客户端
public class UserServerApp {

    public static void main(String[] args) {
        SpringApplication.run(UserServerApp.class, args);
    }
}
