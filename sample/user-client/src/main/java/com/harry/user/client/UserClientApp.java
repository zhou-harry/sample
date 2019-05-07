package com.harry.user.client;

import com.harry.feign.api.UserApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(clients = UserApi.class) // 申明 UserApi 接口作为 Feign Client 调用
@EnableDiscoveryClient // 激活服务发现客户端
public class UserClientApp {

    public static void main(String[] args) {
        SpringApplication.run(UserClientApp.class, args);
    }
}
