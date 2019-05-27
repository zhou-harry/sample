package com.harry.base.excel.validator;

import com.harry.base.excel.util.ValidatorUtil;

/**
 * @author harry
 * @version 1.0
 * @title: EmailValidator
 * @description: TODO
 * @date 2019/5/26 15:25
 */
public class EmailValidator implements ExcelValidator{

    @Override
    public String valid(Object value) {
        String valueString = (String) value;
        return ValidatorUtil.isEmail(valueString) ? null : "[" + valueString + "]不是正确的EMail.";
    }

}
