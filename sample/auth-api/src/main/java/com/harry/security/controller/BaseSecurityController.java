package com.harry.security.controller;

import com.harry.security.domain.BaseSecurityResponse;
import com.harry.security.properties.SecurityProperties;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.harry.security.constant.SecurityConstants.LOGIN_PAGE;

@RestController
@AllArgsConstructor
public class BaseSecurityController {

    //拿到引发跳转的请求(HttpSessionRequestCache把当前的请求缓存到Session中)
    private final RequestCache requestCache = new HttpSessionRequestCache();

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private final SecurityProperties securityProperties;

    //当需要身份认证时，跳转到这里
    @RequestMapping(LOGIN_PAGE)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED) //不是html请求时，返回401状态码
    public BaseSecurityResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //之前缓存的请求（可以拿到引发跳转的请求）
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            //是否以.html结尾，如果是则跳转到登录页面
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                //这个url，用到**Properties 配置文件类来做灵活性配置
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getSigninPageUrl());
            }
        }
        return new BaseSecurityResponse("访问的服务需要身份认证，请引导用户到登录页");
    }

}
