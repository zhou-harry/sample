# 服务组件
## Eureka注册中心：`harry-eureka-server:（9091/9092）`
## 外部化配置服务器：`harry-config-server:7070`
## 外部化配置客户端：`harry-config-client:6060`
## 服务网关：`harry-zuul-server:80`
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
    
## 认证授权组件：`auth-api`
***(授权组件依赖数据库组件,在引用认证授权组件时需参考配置数据源)***
### Web端认证：`auth-web`
- **验证码**
    1. 图形验证码
    - **application.properties配置**
        ```sh
         #验证码位数
         harry.security.validateCode.image.length = 4
         #验证码图片宽
         harry.security.validateCode.image.width = 100
         #验证码图片高
         harry.security.validateCode.image.height = 40
         #过期时间(秒)
         harry.security.validateCode.image.expireIn = 60
         #验证码拦截的url(多个请求需要验证，逗号隔开)
         harry.security.validateCode.image.url = /user,/user/*
         ```
    1. 短信验证码
    - **application.properties配置**
        ```sh
         #手机验证码登录请求处理url
         harry.security.browser.signinProcessUrlMobile = /auth/mobile
         #验证码位数
         harry.security.validateCode.sms.length = 4
         #过期时间(秒)
         harry.security.validateCode.image.expireIn = 60
         #验证码拦截的url(多个请求需要验证，逗号隔开)
         harry.security.validateCode.image.url = /user,/user/*
         ```
- **认证**
    1. 表单认证
    - **application.properties配置**
        ```sh
         #登陆请求方式(JSON/REDIRECT)
         harry.security.browser.loginType = JSON
         #表单身份认证路径
         harry.security.browser.loginPage = /auth/require
         #账号登出请求路径
         harry.security.browser.logoutPage=/signOut
         #表单登录请求处理url
         harry.security.browser.signinProcessUrlForm = /auth/form
         #登录页面地址
         harry.security.browser.signInUrl = /signIn.html
         #注册页面地址
         harry.security.browser.signUpUrl = /signUp.html
         #登出页面地址(默认null)
         harry.security.browser.signOutUrl = /signOut.html
         ```
    1. 第三方认证(QQ,微信)
    - **application.properties配置**
        ```sh
         #社交账号认证处理路径
         harry.security.social.filterProcessUrl = /auth
         #社交认证开关
         harry.security.social.open=true
         
         ##QQ账号认证
         #{filterProcessUrl}/{providerId}组成qq回调路径
         harry.security.social.qq.providerId = qq
         harry.security.social.qq.appId = *****
         harry.security.social.qq.appSecret = *****
         
         ##微信账号认证
         #{filterProcessUrl}/{providerId}组成微信回调路径
         harry.security.social.weixin.providerId = weixin
         harry.security.social.weixin.appId = *****
         harry.security.social.weixin.appSecret = *****
         ```
- **Remember me**
    - **application.properties配置**
        ```sh
         #默认记住账号密码时间(秒)
         harry.security.browser.rememberMeSeconds = 3600
         ```
- **Session**
    - **application.properties配置**
        ```sh
         #同一个用户在系统中的最大session数
         harry.security.browser.session.maximumSessions = 1
         #达到最大session时是否阻止新的登录请求，默认为false，不阻止，新的登录会将老的登录失效掉
         harry.security.browser.session.maxSessionsPreventsLogin = false
         #session失效时跳转的地址
         harry.security.browser.session.sessionInvalidUrl = /session/invalid.html
         
         #Session超时时间
         server.servlet.session.timeout= 30m
         server.servlet.session.cookie.name=HARRY_SESSIONID
         server.servlet.session.cookie.http-only=true
         ```
         1. 单节点Session管理
            ```sh
              #管理Session的方式
              spring.session.store-type=NONE
              
              ```
         1. 集群Session管理
            - 此处需要指定`sessionRegistry`为`redisSessionRegistry`
            ```sh
                http.sessionManagement().sessionRegistry(redisSessionRegistry)
            ```
             ```sh
               #管理Session的方式
               spring.session.store-type=REDIS
               #设置session在redis中的Namespace，避免和其他key冲突
               spring.session.redis.namespace=harry
               #集群Redis
               spring.redis.cluster.nodes=192.168.234.128:7001,192.168.234.128:7002,192.168.234.128:7003,192.168.234.128:7004,192.168.234.128:7005,192.168.234.128:7006
               ```
### App端认证：`auth-app`
- **认证**
    ```sh
        #oauth app token 存储管理方式(redis/jwt)
        harry.security.oauth2.storeType=redis
    ```
    - **redis 管理token store**
        ```sh
             #请求后返回
                 {
                     "access_token": "e9a10b90-db42-4ebc-8974-ba22c8f3d508",
                     "token_type": "bearer",
                     "refresh_token": "a46e4990-a751-4f88-a50a-fdb220332ad9",
                     "expires_in": 43199,
                     "scope": "user_info"
                 }
             #redis中存储信息
                 192.168.234.128:7003> keys *
                 1) "auth:e9a10b90-db42-4ebc-8974-ba22c8f3d508"
                 2) "access_to_refresh:e9a10b90-db42-4ebc-8974-ba22c8f3d508"
                 3) "refresh_to_access:a46e4990-a751-4f88-a50a-fdb220332ad9"
                 4) "refresh_auth:a46e4990-a751-4f88-a50a-fdb220332ad9"
                 5) "auth_to_access:920fdeaaff6e30750c009ef304061658"   
        ```
    - **JWT 管理token store**
        ```sh
             #请求后返回
                 {
                     "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTg3MTIwOTQsInNpZ25pbmciOiJoYXJyeSIsInVzZXJfbmFtZSI6ImhhcnJ5IiwianRpIjoiYTc2N2NmNjEtNzg4MC00ZDg0LThlMjItMjUwYzk4ZTAyOTgxIiwiY2xpZW50X2lkIjoidXNlciIsInNjb3BlIjpbInVzZXJfaW5mbyJdfQ.ODKCYzG7PcqdqmWjS5KjYlhTrz8WBjXUI6LgqJZkAL0",
                     "token_type": "bearer",
                     "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJoYXJyeSIsInNjb3BlIjpbInVzZXJfaW5mbyJdLCJhdGkiOiJhNzY3Y2Y2MS03ODgwLTRkODQtOGUyMi0yNTBjOThlMDI5ODEiLCJleHAiOjE1NjEyNjA4OTQsInNpZ25pbmciOiJoYXJyeSIsImp0aSI6IjRhYTZlNTMyLTA4N2MtNGQ0Ny1iY2VmLTFhMWMyMGIxMGFjZCIsImNsaWVudF9pZCI6InVzZXIifQ.OpI1vscGcJlWa9oUHazL0H_kuyhGBbNumyM97OIDuO4",
                     "expires_in": 43198,
                     "scope": "user_info",
                     "signing": "harry",
                     "jti": "a767cf61-7880-4d84-8e22-250c98e02981"
                 }   
        ```
    1. 图形验证码
    ```sh
        curl -X GET \
            http://localhost/verifyCode/image \
            -H 'deviceId: iphoneX'
    ```  
    2. 短信验证码
    ```sh
        curl -X GET \
          'http://localhost/verifyCode/sms?mobile=17718376207' \
          -H 'deviceId: iphoneX'
    ```  
    1. 表单认证(用户名/密码)
    ```sh
        #请求
        curl -X POST \
          'http://localhost/auth/form?username=harry&password=harry&imageCode=68ff' \
          -H 'Authorization: Basic dXNlcjokMmEkMTAkZndhajJHV3NwU21WUDdNNGlaR3c1dVBNbFBJaG8vaDFwRE03NzdRMnEuYkxldXkyVVgwWlM=' \
          -H 'deviceId: iphoneX'
    ```
    1. 绑定手机号认证
    ```sh
        curl -X POST \
          'http://localhost/auth/mobile?mobile=17718376207&smsCode=0001' \
          -H 'Authorization: Basic dXNlcjokMmEkMTAkZndhajJHV3NwU21WUDdNNGlaR3c1dVBNbFBJaG8vaDFwRE03NzdRMnEuYkxldXkyVVgwWlM=' \
          -H 'deviceId: iphoneX'
    ```
    1. 社交登录认证
    ```sh
        curl -X GET \
          'http://www.harry.com/auth/qq?code=8405A1B52C4E9BBD528C35037E8173AF&state=887f0215-76df-41c1-b3f6-98b29ffc3519' \
          -H 'Authorization: Basic dXNlcjokMmEkMTAkZndhajJHV3NwU21WUDdNNGlaR3c1dVBNbFBJaG8vaDFwRE03NzdRMnEuYkxldXkyVVgwWlM='
    ```
    1. openID认证
    ```sh
        curl -X POST \
          'http://localhost/auth/openId?openId=74CEB1C7E398A56A1FC16A34160EA49F&providerId=qq' \
          -H 'Authorization: Basic dXNlcjokMmEkMTAkZndhajJHV3NwU21WUDdNNGlaR3c1dVBNbFBJaG8vaDFwRE03NzdRMnEuYkxldXkyVVgwWlM='
    ```
    1. OAuth2 授权码模式认证
    ```sh
        #第一步：获取授权码
        localhost/oauth/authorize?response_type=code&client_id=user&redirect_uri=http://localhost:8089/sso/login/oauth2/code/&scope=user_info
        #第二部：根据授权码换取Token
        curl -X POST \
          'http://localhost/oauth/token?grant_type=authorization_code&client_id=user&client_secret=harry&code=QIREAs&redirect_uri=http://localhost:8089/oauth2/code/'
    ```
 #### 授权
 **(实现下面授权url加载接口，并注册到spring容器即可实现url授权逻辑)***
 ```java
     /**
      * @author harry
      * @version 1.0
      * @title: AuthorizeUrlRepository
      * @description: 加载用户所拥有权限的所有url
      * @date 2019/5/25 19:30
      */
     public interface AuthorizeUrlRepository {
         Set<String> loadUrlByUsername(String username);
     }
 ```
 
