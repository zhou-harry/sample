package com.harry.security.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harry.security.domain.BaseSecurityResponse;
import com.harry.security.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author harry
 * @version 1.0
 * @title: CustomerLogoutSuccessHandler
 * @description: 退出系统Handler
 * @date 2019/5/16 23:02
 */
public class CustomerLogoutSuccessHandler implements LogoutSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private ObjectMapper objectMapper = new ObjectMapper();
    private SecurityProperties properties;

    public CustomerLogoutSuccessHandler(SecurityProperties properties) {
        this.properties = properties;
    }


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User principal = (User)authentication.getPrincipal();
        logger.info(principal.getUsername() + "退出系统成功");

        String signOutUrl = properties.getBrowser().getSignOutUrl();
        if (StringUtils.isBlank(signOutUrl)) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(new BaseSecurityResponse("退出成功")));
        } else {
            response.sendRedirect(signOutUrl);
        }
    }

}
