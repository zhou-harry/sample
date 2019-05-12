package com.harry.zuul.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author harry
 * @version 1.0
 * @title: UserController
 * @description: TODO
 * @date 2019/5/12 21:06
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @PostMapping("/regist")
    public void regist(String userId, HttpServletRequest request) {

        //不管是注册用户还是绑定用户，都会拿到一个用户唯一标识。
        providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));
    }

}
