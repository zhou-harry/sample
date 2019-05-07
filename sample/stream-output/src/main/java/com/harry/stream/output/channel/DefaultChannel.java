package com.harry.stream.output.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author harry
 * @version 1.0
 * @title: DefaultChannel
 * @description: 静态消息管道
 * @date 2019/5/7 10:27
 */
public interface DefaultChannel {

    public static final String TOPIC_DEFAULT_OUTUT = "harry_default_output";

    /**
     * 消息输出
     *
     * @return
     */
    @Output(TOPIC_DEFAULT_OUTUT)
    MessageChannel output();


}
