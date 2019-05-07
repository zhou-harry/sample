package com.harry.stream.output;

import com.harry.stream.output.channel.DefaultChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author harry
 * @version 1.0
 * @title: StreamOutputApp
 * @description: 消息发送服务
 * @date 2019/5/7 11:36
 */
@SpringBootApplication
@EnableBinding(DefaultChannel.class) // 激活 Stream Binding 到 DefaultChannel
public class StreamOutputApp {

    public static void main(String[] args) {
        SpringApplication.run(StreamOutputApp.class, args);
    }

}
