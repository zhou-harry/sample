package com.harry.security.web.session.repository;

import com.harry.security.core.constant.ValidateCodeTypeEnum;
import com.harry.security.core.validate.code.ValidateCode;
import com.harry.security.core.validate.code.ValidateCodeRepository;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author harry
 * @version 1.0
 * @title: SessionValidateCodeRepository
 * @description: 默认Session方式管理验证码
 * @date 2019/5/11 23:15
 */
public class SessionValidateCodeRepository implements ValidateCodeRepository {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeTypeEnum codeType) {
        //把请求传递进ServletWebRequest,将随机数存到Session中
        sessionStrategy.setAttribute(request, codeType.getSessionKey(), code);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeTypeEnum codeType) {
        return (ValidateCode) sessionStrategy.getAttribute(request, codeType.getSessionKey());
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeTypeEnum codeType) {
        sessionStrategy.removeAttribute(request, codeType.getSessionKey());
    }
}
