package com.harry.user.client.controller;

import com.harry.feign.api.UserApi;
import com.harry.feign.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 注意：官方建议 客户端和服务端不要同时实现 Feign 接口
 * 实际情况最好使用组合的方式，而不是继承
 */
@RestController
public class UserServiceClientController implements UserApi {

    @Autowired
    private UserApi userService;

    // 通过方法继承，URL 映射 ："/user/save"
    @PostMapping("/user/save")
    public boolean saveUser(User user) {
        return userService.saveUser(user);
    }

    // 通过方法继承，URL 映射 ："/user/find/all"
    @GetMapping("/user/find/all")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/user/find/{username}")
    public User findByName(@PathVariable("username") String userName) {
        return userService.findByName(userName);
    }

}
