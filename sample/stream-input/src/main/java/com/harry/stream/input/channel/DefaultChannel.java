package com.harry.stream.input.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author harry
 * @version 1.0
 * @title: DefaultChannel
 * @description: 静态消息管道
 * @date 2019/5/7 10:27
 */
public interface DefaultChannel {

    public static final String TOPIC_DEFAULT_INPUT="harry_default_input";

    /**
     * 消息输入
     *
     * @return
     */
    @Input(TOPIC_DEFAULT_INPUT)
    SubscribableChannel input();


}
