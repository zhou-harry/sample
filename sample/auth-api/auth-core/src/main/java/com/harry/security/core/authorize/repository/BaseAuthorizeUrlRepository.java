package com.harry.security.core.authorize.repository;

import java.util.HashSet;
import java.util.Set;

/**
 * @author harry
 * @version 1.0
 * @title: BaseAuthorizeUrlRepository
 * @description: TODO
 * @date 2019/5/25 19:33
 */
public class BaseAuthorizeUrlRepository implements AuthorizeUrlRepository {

    @Override
    public Set<String> loadUrlByUsername(String username) {
        Set<String> set = new HashSet<>();

        return set;
    }

}
