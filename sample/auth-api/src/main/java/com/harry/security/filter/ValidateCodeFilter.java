package com.harry.security.filter;

import com.harry.security.exception.ValidateCodeException;
import com.harry.security.properties.SecurityProperties;
import com.harry.security.util.ImageCode;
import com.harry.security.util.SecurityCode;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

//实现InitializingBean接口的目的是：其他的参数都组装完毕之后，初始化urls的值
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {


    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final SecurityProperties securityProperties;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    //存储需要拦截的url
    private Set<String> urls = new HashSet<>();

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    public ValidateCodeFilter(AuthenticationFailureHandler authenticationFailureHandler, SecurityProperties securityProperties) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.securityProperties = securityProperties;
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        //做urls处理
        String[] configUrls = StringUtils.split(securityProperties.getValidateCode().getImage().getUrl(),",");
        for (String configUrl : configUrls) {
            urls.add(configUrl);
        }
        //登录的请求一定要做验证码校验的
        urls.add(SecurityCode.LOGIN_PROCESSINGURL);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //1.判断表单提交的请求（是否为登录请求）
        //因为请求中有/user,/user/*这种方式的请求，就不能使用equals这种方式来判断，需要用到spring的工具类AntPathMatcher
        boolean action = false;
        for (String url : urls) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                action = true;
            }
        }
        if (action) {
            try {
                validate(new ServletWebRequest(request));
                //为什么要用自定义异常，因为这是还是属于认证的过滤链中
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    //校验验证码
    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        ImageCode codeInSession = (ImageCode)sessionStrategy.getAttribute(request, SecurityCode.SESSION_KEY);
        //从请求里，拿到imageCode[来源于表单]
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
        if (StringUtils.isEmpty(codeInRequest)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, SecurityCode.SESSION_KEY);
            throw new ValidateCodeException("验证码已过期");
        }
        if (!codeInSession.getCode().equals(codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(request, SecurityCode.SESSION_KEY);
    }

}
