package com.harry.bus.config;

import com.harry.bus.event.MyRemoteApplicationEvent;
import org.springframework.cloud.bus.event.EnvironmentChangeRemoteApplicationEvent;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @author harry
 * @version 1.0
 * @title: BusServerConfig
 * @description: 自定义监听器配置
 * @date 2019/5/8 23:01
 */
@Configuration
@RemoteApplicationEventScan(basePackageClasses = MyRemoteApplicationEvent.class)
public class BusListenerConfig {

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

    @EventListener
    public void onMyRemoteApplicationEvent(MyRemoteApplicationEvent event) {
        System.out.printf("【自定义MyRemoteApplicationEvent监听器】- " +
                        " Source : %s , originService : %s , destinationService : %s \n",
                event.getSource(),
                event.getOriginService(),
                event.getDestinationService());
    }

}
