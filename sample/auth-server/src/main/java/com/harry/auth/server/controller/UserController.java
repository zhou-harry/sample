package com.harry.auth.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author harry
 * @version 1.0
 * @title: UserController
 * @description: TODO
 * @date 2019/5/21 20:40
 */
@RestController
public class UserController {

    @GetMapping("/user/me")
    public Principal user(Principal principal) {
        return principal;
    }

}
