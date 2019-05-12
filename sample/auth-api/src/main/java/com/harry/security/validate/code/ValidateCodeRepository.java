package com.harry.security.validate.code;

import com.harry.security.constant.ValidateCodeTypeEnum;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author harry
 * @version 1.0
 * @title: ValidateCodeRepository
 * @description: TODO
 * @date 2019/5/11 22:46
 */
public interface ValidateCodeRepository {

    void save(ServletWebRequest request, ValidateCode code, ValidateCodeTypeEnum codeType);

    ValidateCode get(ServletWebRequest request, ValidateCodeTypeEnum codeType);

    void remove(ServletWebRequest request, ValidateCodeTypeEnum codeType);

}
