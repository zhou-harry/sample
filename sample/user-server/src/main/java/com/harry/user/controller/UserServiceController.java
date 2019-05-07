package com.harry.user.controller;

import com.harry.feign.api.UserApi;
import com.harry.feign.domain.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController
public class UserServiceController{

    @Autowired
//    @Qualifier("inMemoryUserService") // 实例Bean ： InMemoryUserService
    @Qualifier("inDBUserService")
    private UserApi userApi;

    private final static Random random = new Random();


    @HystrixCommand(
            commandProperties = { // Command 熔断配置
                    // 设置操作时间为 100 毫秒
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")
            },
            fallbackMethod = "fallbackForSaveUsers" // 设置 fallback 方法
    )
    @PostMapping("/user/save")
    public boolean saveUser(@RequestBody User user) throws InterruptedException{
        if (user == null||user.getId()==null) {
            return false;
        }

        long executeTime = random.nextInt(200);

        // 通过休眠来模拟执行时间
        System.out.println("Save Execute Time : " + executeTime + " ms");

        Thread.sleep(executeTime);

        return userApi.saveUser(user);
    }

    /**
     * 获取所有用户列表
     *
     * @return
     */
    @HystrixCommand(
            commandProperties = { // Command 熔断配置
                    // 设置操作时间为 100 毫秒
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")
            },
            fallbackMethod = "fallbackForGetUsers" // 设置 fallback 方法
    )
    @GetMapping("/user/find/all")
    public List<User> findAll() throws InterruptedException {

        long executeTime = random.nextInt(200);

        // 通过休眠来模拟执行时间
        System.out.println("Find Execute Time : " + executeTime + " ms");

        Thread.sleep(executeTime);

        return userApi.findAll();
    }
    @HystrixCommand(
            commandProperties = { // Command 熔断配置
                    // 设置操作时间为 100 毫秒
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")
            },
            fallbackMethod = "fallbackFindByName" // 设置 fallback 方法
    )
    @GetMapping("/user/find/{username}")
    public User findByName(@PathVariable("username") String userName) throws InterruptedException {

        long executeTime = random.nextInt(200);

        // 通过休眠来模拟执行时间
        System.out.println("Find Execute Time : " + executeTime + " ms");

        Thread.sleep(executeTime);

        return userApi.findByName(userName);
    }

    private List<User>fallbackForGetUsers(){
        return Collections.emptyList();
    }

    private boolean fallbackForSaveUsers(User user){
        return false;
    }

    public User fallbackFindByName(String userName){
        User user = new User();
        user.setName("请求超时！");
        return user;
    }
}
