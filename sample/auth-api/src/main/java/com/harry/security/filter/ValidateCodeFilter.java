package com.harry.security.filter;

import com.harry.security.constant.ValidateCodeTypeEnum;
import com.harry.security.exception.ValidateCodeException;
import com.harry.security.properties.SecurityProperties;
import com.harry.security.validate.code.ValidateCode;
import com.harry.security.validate.code.ValidateCodeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.harry.security.constant.SecurityConstants.SESSION_KEY_IMAGE;

/**
 * 验证码配置过滤器(适配图形验证码，短信验证码)
 * @author harry
 * @version 1.0
 * @title: ValidateCodeFilter
 * @description: 实现InitializingBean接口的目的是：其他的参数都组装完毕之后，初始化urls的值
 * @date 2019/5/12 0:32
 */
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {


    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final SecurityProperties securityProperties;

    private final ValidateCodeRepository validateCodeRepository;

    private Map<String, ValidateCodeTypeEnum> urlMap = new LinkedHashMap<>();

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    //存储需要拦截的url
    private Set<String> urls = new HashSet<>();

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    public ValidateCodeFilter(AuthenticationFailureHandler authenticationFailureHandler, SecurityProperties securityProperties, ValidateCodeRepository validateCodeRepository) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.securityProperties = securityProperties;
        this.validateCodeRepository = validateCodeRepository;
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
        String[] validateCodeUrls = StringUtils.splitByWholeSeparator(validateCodeUrl, ",");
        for (String url : validateCodeUrls) {
            urlMap.put(url, validateCodeType);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        logger.info("ValidateCodeFilter: " + request.getRequestURL());

        ValidateCodeTypeEnum validateCodeType = null;
        for (Map.Entry<String, ValidateCodeTypeEnum> entry : urlMap.entrySet()) {
            if (pathMatcher.match(entry.getKey(), request.getRequestURI())) {
                validateCodeType = entry.getValue();
                break;
            }
        }

        if (validateCodeType != null) {
            try {
                validate(new ServletWebRequest(request), validateCodeType);
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    //校验验证码
    private void validate(ServletWebRequest request,ValidateCodeTypeEnum validateCodeType) throws ServletRequestBindingException {

        ValidateCode codeInRepository = validateCodeRepository.get(request, validateCodeType);

        if (codeInRepository == null) {
            throw new ValidateCodeException(String.format("需要%s验证码", validateCodeType));
        }

        //从请求里，拿到code[来源于表单]
        String codeInRequest = request.getParameter(validateCodeType.getParameterNameOnValidate());
        // 验证码不能为空
        if (StringUtils.isEmpty(codeInRequest)) {
            throw new ValidateCodeException("请填写验证码");
        }

        // 验证码不存在
        if (codeInRequest == null) {
            throw new ValidateCodeException("请先获取验证码");
        }

        // 验证码已过期
        if (codeInRepository.isExpired()) {
            validateCodeRepository.remove(request, validateCodeType);
            throw new ValidateCodeException("验证码已过期");
        }

        // 验证码不匹配
        if (!StringUtils.equalsIgnoreCase(codeInRepository.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(request, SESSION_KEY_IMAGE);
    }

}
