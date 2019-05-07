package com.harry.config.client;

import com.harry.config.client.domain.UserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Set;

@SpringBootApplication
@EnableConfigurationProperties(UserProperties.class)
@EnableScheduling
@EnableEurekaClient
public class ConfigClientApp {

    private final ContextRefresher contextRefresher;

    @Autowired
    public ConfigClientApp(ContextRefresher contextRefresher) {
        this.contextRefresher = contextRefresher;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApp.class, args);
    }

    @Scheduled(fixedRate = 1000L)
    public void refresher() {
        Set<String> keys = contextRefresher.refresh();

        if (!keys.isEmpty()) {
            System.out.println("监测到配置文件的变化：" + keys);
        }

    }

    @Bean
    public MyHealthIndicator myHealthIndicator() {
        return new MyHealthIndicator();
    }

    private class MyHealthIndicator implements HealthIndicator {

        @Override
        public Health health() {
            Health.Builder builder = Health.status(Status.UP);
            builder.withDetail("name", "MyHealthIndicator");
            builder.withDetail("timestamp", System.currentTimeMillis());
            return builder.build();
        }
    }

}
