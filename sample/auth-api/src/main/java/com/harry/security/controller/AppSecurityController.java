package com.harry.security.controller;

import com.harry.security.domain.SocialUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author harry
 * @version 1.0
 * @title: AppSecurityController
 * @description: TODO
 * @date 2019/5/12 21:19
 */
@RestController
@RequestMapping("social")
public class AppSecurityController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;


    @GetMapping("/info")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request){
        SocialUserInfo userInfo = new SocialUserInfo();
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));

        if (connection != null)  {
            userInfo.setProviderId(connection.getKey().getProviderId());
            userInfo.setProviderUserId(connection.getKey().getProviderUserId());
            userInfo.setNickname(connection.getDisplayName());
            userInfo.setHeadimg(connection.getImageUrl());
        }

        return userInfo;
    }

}
