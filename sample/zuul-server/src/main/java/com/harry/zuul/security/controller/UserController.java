package com.harry.zuul.security.controller;

import com.harry.security.core.social.AppSignupUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author harry
 * @version 1.0
 * @title: UserController
 * @description: TODO
 * @date 2019/5/12 21:06
 */
@RestController
public class UserController {

    private Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private ProviderSignInUtils providerSignInUtils;
    @Autowired(required = false)
    private AppSignupUtils appSignupUtils;

    @PostMapping("/user/regist")
    public void regist(String userId, HttpServletRequest request) {
        logger.debug("第三方账号绑定注册");
        //不管是注册用户还是绑定用户，都会拿到一个用户唯一标识。
        providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));
        if (appSignupUtils != null) {
            appSignupUtils.doPostSignUp(userId, new ServletWebRequest(request));
        }
    }

    @GetMapping("/user/principal")
    public Principal user(Principal principal) {
        return principal;
    }

}
