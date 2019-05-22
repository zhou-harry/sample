package com.harry.security.web.session;

/**
 * 方式1、在配置文件中添加配置
 * spring.session.store-type=redis
 * 方式2、添加注解：@EnableRedisHttpSession
 *
 * @author harry
 * @version 1.0
 * @EnableRedisHttpSession
 * @title: SessionConfig
 * @description: 将Session交给Redis管理
 * @date 2019/5/15 21:48
 */
//@Configuration
public class SessionConfig {

    /**
     * Session交给Redis管理，
     * 如果未加spring redis启动器的话则需要加lettuce-core依赖，并自己创建Redis factory
     */
//    @Bean
//    public LettuceConnectionFactory connectionFactory() {
//        String[] strings = StringUtils.splitByWholeSeparator("192.168.234.128:7001,192.168.234.128:7002,192.168.234.128:7003,192.168.234.128:7004,192.168.234.128:7005,192.168.234.128:7006", ",");
//        RedisClusterConfiguration configuration = new RedisClusterConfiguration(Arrays.asList(strings));
//
//        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration);
//        factory.setTimeout(10000);
//        return factory;
//    }
//    @Bean
//    public HttpSessionStrategy httpSessionStrategy() {
    //使用HTTP的头来取代使用 cookie 传送当前session信息
//        return new HeaderHttpSessionStrategy();
    //如果需要使用cookie来传送 session 信息。
//        return new CookieHttpSessionStrategy();
//    }

}
