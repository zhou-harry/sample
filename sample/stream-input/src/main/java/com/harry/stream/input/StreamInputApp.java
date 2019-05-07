package com.harry.stream.input;

import com.harry.stream.input.channel.DefaultChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author harry
 * @version 1.0
 * @title: StreamInputApp
 * @description: TODO
 * @date 2019/5/7 11:44
 */
@SpringBootApplication
@EnableBinding(DefaultChannel.class) // 激活 Stream Binding 到 DefaultChannel
public class StreamInputApp {

    public static void main(String[] args) {
        SpringApplication.run(StreamInputApp.class, args);
    }

}
