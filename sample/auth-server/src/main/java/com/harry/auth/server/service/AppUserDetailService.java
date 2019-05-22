package com.harry.auth.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author harry
 * @version 1.0
 * @title: AppUserDetailService
 * @description: TODO
 * @date 2019/5/22 15:34
 */
@Component("userDetailsService")
public class AppUserDetailService implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //根据手机号查询数据库用户信息

        //将查询出的的用户信息组装并返回
        User user = new User(
                username,
                passwordEncoder.encode("harry"),
                Collections.emptyList()
        );
        return user;
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        //根据手机号查询数据库用户信息

        //将查询出的的用户信息组装并返回
        SocialUser user = new SocialUser(
                userId,
                passwordEncoder.encode("harry"),
                Collections.emptyList()
        );
        return user;
    }
}
