package com.harry.feign.fallback;

import com.harry.feign.api.UserApi;
import com.harry.feign.domain.User;

import java.util.Collections;
import java.util.List;

public class UserServiceFallback implements UserApi {

    @Override
    public boolean saveUser(User user) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return Collections.emptyList();
    }

    @Override
    public User findByName(String userName) {
        User user = new User();
        user.setName(userName+",无此账号！");
        return user;
    }

}
