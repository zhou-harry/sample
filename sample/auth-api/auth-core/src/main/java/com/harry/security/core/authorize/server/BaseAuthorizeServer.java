package com.harry.security.core.authorize.server;

import com.harry.security.core.authorize.repository.AuthorizeUrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @author harry
 * @version 1.0
 * @title: BaseAuthorizeServer
 * @description: 权限检查服务基本实现
 * @date 2019/5/25 17:25
 */
public class BaseAuthorizeServer implements AuthorizeServer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private AuthorizeUrlRepository authorizeUrlRepository;

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object principle = authentication.getPrincipal();

        boolean hasPermission = false;

        if (principle == null) {
            return hasPermission;
        }
        Set<String> urls =null;
        if (principle instanceof UserDetails) {
            //Web端认证后端用户信息格式
            UserDetails userDetails = (UserDetails) principle;
            String username = userDetails.getUsername();
            urls = authorizeUrlRepository.loadUrlByUsername(username);
        }else if(principle instanceof String){
            //App端认证后的用户信息格式
            urls = authorizeUrlRepository.loadUrlByUsername(principle.toString());
        }
        if (urls == null) {
            return hasPermission;
        }
        for (String url : urls) {
            if (antPathMatcher.match(url, request.getRequestURI())) {
                hasPermission = true;
                break;
            }
        }
        return hasPermission;
    }

}
