package com.harry.auth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author harry
 * @version 1.0
 * @title: AuthClientApp
 * @description: TODO
 * @date 2019/5/20 23:22
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AuthServerApp {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApp.class, args);
    }
}
