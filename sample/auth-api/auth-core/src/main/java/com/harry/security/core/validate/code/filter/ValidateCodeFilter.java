package com.harry.security.core.validate.code.filter;

import com.harry.security.core.constant.ValidateCodeTypeEnum;
import com.harry.security.core.exception.ValidateCodeException;
import com.harry.security.core.properties.SecurityProperties;
import com.harry.security.core.validate.code.ValidateCodeProcessorHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 验证码配置过滤器(适配图形验证码，短信验证码)
 *
 * @author harry
 * @version 1.0
 * @title: ValidateCodeFilter
 * @description: 实现InitializingBean接口的目的是：其他的参数都组装完毕之后，初始化urls的值
 * @date 2019/5/12 0:32
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {


    /**
     * 验证码校验失败处理器
     */
    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final SecurityProperties securityProperties;

    private final ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 所有需要校验验证码的url
     */
    private Map<String, ValidateCodeTypeEnum> urlMap = new LinkedHashMap<>();

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    public ValidateCodeFilter(AuthenticationFailureHandler authenticationFailureHandler, SecurityProperties securityProperties, ValidateCodeProcessorHolder validateCodeProcessorHolder) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.securityProperties = securityProperties;
        this.validateCodeProcessorHolder = validateCodeProcessorHolder;
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        //做urls处理
        urlMap.put(securityProperties.getBrowser().getSigninProcessUrlForm(), ValidateCodeTypeEnum.IMAGE);
        urlMap.put(securityProperties.getBrowser().getSigninProcessUrlMobile(), ValidateCodeTypeEnum.SMS);

        addUrlToMap(securityProperties.getValidateCode().getImage().getUrl(), ValidateCodeTypeEnum.IMAGE);
        addUrlToMap(securityProperties.getValidateCode().getSms().getUrl(), ValidateCodeTypeEnum.SMS);
    }

    private void addUrlToMap(String validateCodeUrl, ValidateCodeTypeEnum validateCodeType) {
        if (StringUtils.isBlank(validateCodeUrl)) {
            return;
        }
        String[] validateCodeUrls = StringUtils.splitByWholeSeparator(validateCodeUrl, ",");
        for (String url : validateCodeUrls) {
            urlMap.put(url, validateCodeType);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        logger.info("ValidateCodeFilter: " + request.getRequestURL());

        //获取校验码的类型，如果当前请求不需要校验，则validateCodeType=null
        ValidateCodeTypeEnum validateCodeType = null;
        for (Map.Entry<String, ValidateCodeTypeEnum> entry : urlMap.entrySet()) {
            if (pathMatcher.match(entry.getKey(), request.getRequestURI())) {
                validateCodeType = entry.getValue();
                break;
            }
        }

        if (validateCodeType != null) {
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(validateCodeType)
                        .validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
