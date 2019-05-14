package com.harry.security.constant;

/**
 * @author harry
 * @version 1.0
 * @title: ValidateCodeTypeEnum
 * @description: TODO
 * @date 2019/5/11 22:47
 */
public enum  ValidateCodeTypeEnum {

    IMAGE {
        @Override
        public String getParameterNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }

        @Override
        public String getSessionKey() {
            return SecurityConstants.SESSION_KEY_IMAGE;
        }

        @Override
        public String getProcessor() {
            return SecurityConstants.DEFAULT_IMAGE_CODE_PROCESSOR;
        }
    },
    SMS {
        @Override
        public String getParameterNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }

        @Override
        public String getSessionKey() {
            return SecurityConstants.SESSION_KEY_SMS;
        }

        @Override
        public String getProcessor() {
            return SecurityConstants.DEFAULT_SMS_CODE_PROCESSOR;
        }
    };

    public abstract String getParameterNameOnValidate();

    public abstract String getSessionKey();

    public abstract String getProcessor();

}
