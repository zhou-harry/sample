package com.harry.bus.config;

import org.springframework.cloud.bus.event.EnvironmentChangeRemoteApplicationEvent;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @author harry
 * @version 1.0
 * @title: BusServerConfig
 * @description: TODO
 * @date 2019/5/8 23:01
 */
@Configuration
public class BusServerConfig {

    /**
     * 自定义 RefreshRemoteApplicationEvent 监听器
     * @param event
     */
    @EventListener
    public void onRefreshRemoteApplicationEvent(RefreshRemoteApplicationEvent event) {

        System.out.printf("【自定义RefreshRemoteApplicationEvent 监听器】 Source : %s , originService : %s , destinationService : %s \n",
                event.getSource(),
                event.getOriginService(),
                event.getDestinationService());

    }

    @EventListener
    public void onEnvironmentChangeRemoteApplicationEvent(EnvironmentChangeRemoteApplicationEvent event) {

        System.out.printf("【自定义EnvironmentChangeRemoteApplicationEvent监听器】" +
                        " Source : %s , originService : %s , destinationService : %s \n",
                event.getSource(),
                event.getOriginService(),
                event.getDestinationService());

    }

}
