package com.harry.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harry.security.constant.AuthenticationResponseTypeEnum;
import com.harry.security.domain.BaseSecurityResponse;
import com.harry.security.properties.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("baseAuthenticationFailureHandler")
public class BaseAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final ObjectMapper objectMapper;
    private final SecurityProperties securityProperties;

    public BaseAuthenticationFailureHandler(ObjectMapper objectMapper, SecurityProperties securityProperties) {
        this.objectMapper = objectMapper;
        this.securityProperties = securityProperties;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        if (AuthenticationResponseTypeEnum.JSON.equals(securityProperties.getBrowser().getLoginType())){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(new BaseSecurityResponse(exception.getMessage())));
        }else {
            super.onAuthenticationFailure(request,response,exception);
        }

    }

}
