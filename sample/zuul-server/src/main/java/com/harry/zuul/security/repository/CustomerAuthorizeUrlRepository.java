package com.harry.zuul.security.repository;

import com.harry.security.core.authorize.repository.AuthorizeUrlRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author harry
 * @version 1.0
 * @title: CustomerAuthorizeUrlRepository
 * @description: TODO
 * @date 2019/5/25 19:49
 */
@Component
public class CustomerAuthorizeUrlRepository implements AuthorizeUrlRepository {

    @Override
    public Set<String> loadUrlByUsername(String username) {

        // TODO: 2019/5/25  从数据库查询用户所拥有权限的所有url
        Set<String> urls=new HashSet<>();
        urls.add("/index.html");
        urls.add("/user/*");

        return urls;
    }

}
