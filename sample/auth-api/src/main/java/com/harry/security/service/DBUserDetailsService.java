package com.harry.security.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component("dbUserDetailsService")
@AllArgsConstructor
public class DBUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

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
}
