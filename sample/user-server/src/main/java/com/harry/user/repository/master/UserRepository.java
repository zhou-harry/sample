package com.harry.user.repository.master;

import com.harry.feign.domain.User;
import com.harry.user.repository.BaseRepository;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository extends BaseRepository<User> {

}
