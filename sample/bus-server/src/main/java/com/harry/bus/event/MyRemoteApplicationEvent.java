package com.harry.bus.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * @author harry
 * @version 1.0
 * @title: MyRemoteApplicationEvent
 * @description: 自定义实现 RemoteApplicationEvent
 * @date 2019/5/9 16:49
 */
public class MyRemoteApplicationEvent extends RemoteApplicationEvent {


    @SuppressWarnings("unused")
    private MyRemoteApplicationEvent() {
        // for serializers
    }

    public MyRemoteApplicationEvent(String source, String originService,
                                    String destinationService) {
        super(source, originService, destinationService);
    }
}
