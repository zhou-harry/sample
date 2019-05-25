package com.harry.security.core.social;

import com.harry.security.core.exception.AppSecurityException;
import com.harry.security.core.util.AuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

/**
 * @author harry
 * @version 1.0
 * @title: AppSignupUtils
 * @description: App端绑定第三方账号信息注册
 * @date 2019/5/24 20:56
 */
@Component
@ConditionalOnBean(type = "com.harry.security.web.config.WebSecurityBeanConfig")
public class AppSignupUtils {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final RedisTemplate redisTemplate;
    private final UsersConnectionRepository usersConnectionRepository;
    private final ConnectionFactoryLocator connectionFactoryLocator;

    public AppSignupUtils(RedisTemplate redisTemplate, UsersConnectionRepository usersConnectionRepository, ConnectionFactoryLocator connectionFactoryLocator) {
        this.redisTemplate = redisTemplate;
        this.usersConnectionRepository = usersConnectionRepository;
        this.connectionFactoryLocator = connectionFactoryLocator;
    }


    public void saveConnectionData(WebRequest request, ConnectionData connectionData) {
        logger.debug("存储第三方社交账号信息");
        redisTemplate.opsForValue().set(getKey(request), connectionData);
    }

    public void doPostSignUp(String userId, WebRequest request) {
        logger.debug("读取第三方社交账号信息");
        String key = getKey(request);

        if (!redisTemplate.hasKey(key)) {
            throw new AppSecurityException("无法找到缓存的第三方社交账号信息");
        }

        ConnectionData connectionData = (ConnectionData) redisTemplate.opsForValue().get(key);

        Connection<?> connection = connectionFactoryLocator.getConnectionFactory(
                connectionData.getProviderId()
        ).createConnection(connectionData);
        //持久化操作
        usersConnectionRepository.createConnectionRepository(userId).addConnection(connection);

        redisTemplate.delete(key);
    }


    private String getKey(WebRequest request) {

        String deviceId = AuthUtil.getDeviceId(request);

        return "connectiondata:" + deviceId;
    }

}
