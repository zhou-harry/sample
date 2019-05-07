package com.harry.config.client.controller;

import com.harry.config.client.domain.UserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("user")
@RestController
public class UserController {

    private final UserProperties userProperties;

    @Autowired
    public UserController(UserProperties userProperties) {
        this.userProperties = userProperties;
    }

    @GetMapping("get")
    public UserProperties get(){

        return userProperties;
    }
}
