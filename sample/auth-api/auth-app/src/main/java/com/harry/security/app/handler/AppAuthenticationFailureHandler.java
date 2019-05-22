package com.harry.security.app.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harry.security.core.constant.AuthenticationResponseTypeEnum;
import com.harry.security.core.domain.BaseSecurityResponse;
import com.harry.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author harry
 * @version 1.0
 * @title: AppAuthenticationFailureHandler
 * @description: App 权限认证失败处理器
 * @date 2019/5/22 11:08
 */
public class AppAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        if (AuthenticationResponseTypeEnum.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(new BaseSecurityResponse(exception.getMessage())));
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }

    }
}