package com.harry.security.core.authorize.server;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author harry
 * @version 1.0
 * @title: AuthorizeServer
 * @description: 权限检查服务
 * @date 2019/5/25 17:23
 */
public interface AuthorizeServer {

    boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
