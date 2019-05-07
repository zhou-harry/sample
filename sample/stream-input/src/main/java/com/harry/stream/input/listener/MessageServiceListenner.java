package com.harry.stream.input.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harry.common.domain.DefaultDomain;
import com.harry.stream.input.channel.DefaultChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * @author harry
 * @version 1.0
 * @title: MessageServiceListenner
 * @description: 消息服务接收监听
 * @date 2019/5/7 10:46
 */
@Service
public class MessageServiceListenner {

    @Autowired
    private DefaultChannel defaultChannel;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        SubscribableChannel subscribableChannel = defaultChannel.input();
        subscribableChannel.subscribe(message -> {
            System.out.println("Subscribe by SubscribableChannel");
            // message body 是字节流 byte[]
            byte[] body = (byte[]) message.getPayload();

            ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                DefaultDomain data = (DefaultDomain) objectInputStream.readObject(); // 反序列化成 User 对象
                System.out.println(data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @StreamListener(DefaultChannel.TOPIC_DEFAULT_INPUT)
    public void onMessage(@Payload DefaultDomain data, @Headers Map headers) throws IOException {
        System.out.println("StreamListener监听内容：" + data);
        /**
         * @StreamListener 订阅处理出异常后，通过@ServiceActivator订阅错误通道再次接收此消息
         */
        throw new RuntimeException("throw error");
    }

    @ServiceActivator(inputChannel = DefaultChannel.TOPIC_DEFAULT_INPUT)
    public void listen(Message<?> message) throws IOException {
        System.out.println("ServiceActivator Handling ERROR: " + message);
    }

}
