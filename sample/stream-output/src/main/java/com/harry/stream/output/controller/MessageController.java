package com.harry.stream.output.controller;

import com.harry.common.domain.DefaultDomain;
import com.harry.stream.output.channel.DefaultChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * @author harry
 * @version 1.0
 * @title: MessageSendController
 * @description: 消息发送Rest
 * @date 2019/5/7 10:32
 */
@RestController
@RequestMapping("output")
public class MessageController {

    //静态绑定Channel
    @Autowired
    private DefaultChannel defaultChannel;
    //动态绑定Channel
    @Autowired
    private BinderAwareChannelResolver resolver;

    /**
     * 使用静态绑定Channel发送消息
     * @param id
     * @param name
     * @return
     */
    @PostMapping("default/{id}")
    public String handleRequest(@PathVariable("id") Long id,
                                @RequestParam String name) {

        MessageChannel messageChannel = defaultChannel.output();

        DefaultDomain domain = new DefaultDomain(id, name);

        messageChannel.send(MessageBuilder.withPayload(domain).build());
        return "OK";
    }

    /**
     * 使用动态绑定Channel发送消息
     *
     * @param body
     * @param target
     * @param contentType
     */
    @PostMapping(path = "custom/{target}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void handleRequest(@RequestBody String body,
                              @PathVariable("target") String target,
                              @RequestHeader(HttpHeaders.CONTENT_TYPE) Object contentType) {
        resolver.resolveDestination(target)
                .send(
                        MessageBuilder.createMessage(
                                body,
                                new MessageHeaders(
                                        Collections.singletonMap(MessageHeaders.CONTENT_TYPE, contentType)
                                )
                        )
                );
    }

}
