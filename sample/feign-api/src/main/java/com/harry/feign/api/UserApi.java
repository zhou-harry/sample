package com.harry.feign.api;

import com.harry.feign.domain.User;
import com.harry.feign.fallback.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@FeignClient(
        value = "${provider.service.name}",
        fallback = UserServiceFallback.class
)//利用占位符避免未来整合硬编码
public interface UserApi {

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    @PostMapping("/user/save")
    boolean saveUser(@RequestBody User user);
    /**
     * 查询所有的用户列表
     *
     * @return non-null
     */
    @GetMapping("/user/find/all")
    List<User> findAll();

    /**
     * 根据账号查询用户
     * @param userName
     * @return
     */
    @GetMapping("/user/find/{username}")
    User findByName(@PathVariable("username") String userName);

}
