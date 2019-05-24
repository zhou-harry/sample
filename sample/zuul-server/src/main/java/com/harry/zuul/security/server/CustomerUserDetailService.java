package com.harry.zuul.security.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @title: CustomerUserDetailService
 * @description: TODO
 * @date 2019/5/12 16:14
 */
@Component("userDetailsService")
public class CustomerUserDetailService implements UserDetailsService, SocialUserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.debug("查询用户信息："+username);
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
