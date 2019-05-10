package com.harry.bus.controller;

import com.harry.bus.event.MyRemoteApplicationEvent;
import org.springframework.beans.BeansException;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author harry
 * @version 1.0
 * @title: BusEventController
 * @description: TODO
 * @date 2019/5/9 16:54
 */
@RestController
public class BusEventController implements ApplicationContextAware, ApplicationEventPublisherAware {

    private ApplicationEventPublisher eventPublisher;

    private ApplicationContext applicationContext;

    private final BusProperties bus;

    public BusEventController(BusProperties bus) {
        this.bus = bus;
    }

    @PostMapping("/bus/event/publish/customer")
    public boolean publishUserEvent(@RequestParam String userName,
                                    @RequestParam(value = "destination", required = false) String destination) {

        String serviceInstanceId = bus.getId();//applicationContext.getId();

        MyRemoteApplicationEvent event = new MyRemoteApplicationEvent(
                userName,
                serviceInstanceId,
                destination
        );

        eventPublisher.publishEvent(event);

        return true;

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }
}
