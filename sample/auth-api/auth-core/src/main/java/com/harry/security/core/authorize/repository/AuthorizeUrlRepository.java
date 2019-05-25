package com.harry.security.core.authorize.repository;

import java.util.Set;

/**
 * @author harry
 * @version 1.0
 * @title: AuthorizeUrlRepository
 * @description: 加载用户所拥有权限的所有url
 * @date 2019/5/25 19:30
 */
public interface AuthorizeUrlRepository {

    Set<String> loadUrlByUsername(String username);

}
