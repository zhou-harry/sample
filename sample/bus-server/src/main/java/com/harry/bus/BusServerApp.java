package com.harry.bus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author harry
 * @version 1.0
 * @title: BusServerApp
 * @description: 消息总线服务
 * @date 2019/5/8 21:45
 */
@SpringBootApplication
@EnableDiscoveryClient //激活服务发现客户端
public class BusServerApp {

    public static void main(String[] args) {
        SpringApplication.run(BusServerApp.class,args);
    }

}
