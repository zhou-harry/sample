package com.harry.security.core.validate.code;

import com.harry.security.core.constant.ValidateCodeTypeEnum;
import com.harry.security.core.exception.ValidateCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author harry
 * @version 1.0
 * @title: ValidateCodeProcessorHolder
 * @description: TODO
 * @date 2019/5/14 16:35
 */
@Component
public class ValidateCodeProcessorHolder {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeTypeEnum type) {

        ValidateCodeProcessor processor = validateCodeProcessors.get(type.getProcessor());
        if (processor == null) {
            throw new ValidateCodeException("验证码处理器" + type.getProcessor() + "不存在");
        }
        return processor;
    }

}
