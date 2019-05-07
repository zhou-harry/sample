package com.harry.user.server;

import com.harry.feign.api.UserApi;
import com.harry.feign.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("inMemoryUserService")
public class InMemoryUserService implements UserApi {

    private Map<Long, User> repository = new ConcurrentHashMap<>();

    @Override
    public boolean saveUser(User user) {
        return repository.put(user.getId(), user) == null;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList(repository.values());
    }

    @Override
    public User findByName(String userName) {
        return null;
    }
}
