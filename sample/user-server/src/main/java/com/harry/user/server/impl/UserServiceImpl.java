package com.harry.user.server.impl;

import com.harry.feign.api.UserApi;
import com.harry.feign.domain.User;
import com.harry.user.repository.BaseRepository;
import com.harry.user.repository.master.UserRepository;
import com.harry.user.server.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service("inDBUserService")
@AllArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<User>  implements UserService, UserApi {

    private final UserRepository userRepository;

    @Override
    protected BaseRepository<User> getMapper() {
        return this.userRepository;
    }

    @Override
    public boolean saveUser(User user) {
        return this.insert(user);
    }

    @Override
    public User findByName(String userName) {
        User user = new User();
        user.setName(userName);

        return this.findByEntity(user);
    }
}
