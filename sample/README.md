# 服务组件
## Eureka注册中心：`harry-eureka-server:（9091/9092）`
## 外部化配置服务器：`harry-config-server:7070`
## 外部化配置客户端：`harry-config-client:6060`
## 服务网关：`harry-zuul-server:10000`
## 用户服务提供者：`harry-user-server:8080`
## 用户服务客户端：`harry-user-client:8081`
## 邮件服务器：`harry-mail-server:5050`
## 消息驱动发送服务器：`harry-stream-output:4040`
## 消息驱动接收服务器：`harry-stream-input:4041`
## 消息总线服务器：`harry-bus-server:10001`
# 公共组件
## 服务调用组件：`feign-api`
## 数据库连接组件：`database-api`
#### 服务端配置
1. 依赖 Database API
    ```sh
    <dependency>
        <groupId>com.harry</groupId>
        <artifactId>database-api</artifactId>
        <version>${project.version}</version>
    </dependency>
    ```
1. 排除Spring对数据源的自动化配置
    ```sh
    //database-api中存在数据库组件，此处防止autoconfig读取spring数据源配置
    @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
    ```
- **配置application.properties**
1. 数据源方式
    ```sh
    #（动态数据源与静态数据源切换开关）
    harry.datasource.dynamic=true
    ```
1. 静态（单）数据源配置方式
    ```sh
    #mybatis Mapper路径
    static.datasource.packageLocation = com.harry.zuul.repository.master
    static.datasource.mapperLocation = classpath:mapper/master/*.xml
    # 静态数据源
    static.datasource.source.url=jdbc:mysql://localhost:3306/harry?useUnicode=true&serverTimezone=CTT&characterEncoding=utf8&allowMultiQueries=true
    static.datasource.source.username=harry
    static.datasource.source.password=abcd1234
    static.datasource.source.driverClass=com.mysql.cj.jdbc.Driver
    ## 连接池的配置信息
    # 初始化大小，最小，最大
    static.datasource.source.type=com.alibaba.druid.pool.DruidDataSource
    static.datasource.source.initialSize=5
    static.datasource.source.minIdle=5
    static.datasource.source.maxActive=20
    # 配置获取连接等待超时的时间
    static.datasource.source.maxWait=60000
    # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
    static.datasource.source.timeBetweenEvictionRunsMillis=60000
    # 配置一个连接在池中最小生存的时间，单位是毫秒
    static.datasource.source.minEvictableIdleTimeMillis=300000
    static.datasource.source.validationQuery=SELECT 1 FROM DUAL
    static.datasource.source.testWhileIdle=true
    static.datasource.source.testOnBorrow=false
    static.datasource.source.testOnReturn=false
    # 打开PSCache，并且指定每个连接上PSCache的大小
    static.datasource.source.poolPreparedStatements=true
    static.datasource.source.maxPoolPreparedStatementPerConnectionSize=20
    # 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
    static.datasource.source.filters=stat,wall,log4j
    # 通过connectProperties属性来打开mergeSql功能；慢SQL记录
    static.datasource.source.connectionProperties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
    ```
1. 动态（多）数据源配置方式
    ```sh
    dynamic.datasource.packageLocation = com.harry.zuul.repository.master
    dynamic.datasource.mapperLocation = classpath:mapper/master/*.xml
    
    dynamic.datasource.sources[0].url=jdbc:mysql://localhost:3306/harry?useUnicode=true&serverTimezone=CTT&characterEncoding=utf8&allowMultiQueries=true
    dynamic.datasource.sources[0].username=harry
    dynamic.datasource.sources[0].password=abcd1234
    dynamic.datasource.sources[0].driverClass=com.mysql.cj.jdbc.Driver
    ## 连接池的配置信息
    # 初始化大小，最小，最大
    dynamic.datasource.sources[0].type=com.alibaba.druid.pool.DruidDataSource
    dynamic.datasource.sources[0].initialSize=5
    dynamic.datasource.sources[0].minIdle=5
    dynamic.datasource.sources[0].maxActive=20
    # 配置获取连接等待超时的时间
    dynamic.datasource.sources[0].maxWait=60000
    # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
    dynamic.datasource.sources[0].timeBetweenEvictionRunsMillis=60000
    # 配置一个连接在池中最小生存的时间，单位是毫秒
    dynamic.datasource.sources[0].minEvictableIdleTimeMillis=300000
    dynamic.datasource.sources[0].validationQuery=SELECT 1 FROM DUAL
    dynamic.datasource.sources[0].testWhileIdle=true
    dynamic.datasource.sources[0].testOnBorrow=false
    dynamic.datasource.sources[0].testOnReturn=false
    # 打开PSCache，并且指定每个连接上PSCache的大小
    dynamic.datasource.sources[0].poolPreparedStatements=true
    dynamic.datasource.sources[0].maxPoolPreparedStatementPerConnectionSize=20
    # 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
    dynamic.datasource.sources[0].filters=stat,wall,log4j
    # 通过connectProperties属性来打开mergeSql功能；慢SQL记录
    dynamic.datasource.sources[0].connectionProperties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
    ```
    
##认证授权组件：`auth-api`
***(授权组件依赖数据库组件,在引用认证授权组件时需参考配置数据源)***
```sh
 ###权限控制
 #权限登录地址
 harry.security.browser.loginPage=/login.html
 #权限登录方式
 harry.security.browser.loginType=REDIRECT
 ##验证码
 #验证码位数
 harry.security.validateCode.image.length = 4
 harry.security.validateCode.image.width = 60
 harry.security.validateCode.image.height = 25
 ####验证码拦截的url
 harry.security.validateCode.image.url = /user,/user/*
 ```
 
 
