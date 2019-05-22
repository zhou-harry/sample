package com.harry.security.core.validate.code;

import com.harry.security.core.constant.ValidateCodeTypeEnum;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author harry
 * @version 1.0
 * @title: ValidateCodeRepository
 * @description: 验证码持久化仓库
 * @date 2019/5/11 22:46
 */
public interface ValidateCodeRepository {

    void save(ServletWebRequest request, ValidateCode code, ValidateCodeTypeEnum codeType);

    ValidateCode get(ServletWebRequest request, ValidateCodeTypeEnum codeType);

    void remove(ServletWebRequest request, ValidateCodeTypeEnum codeType);

}
