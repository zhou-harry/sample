package com.harry.sample.client.excel.validate;

import com.google.common.collect.Lists;
import com.harry.base.excel.validator.ExcelValidator;

import java.util.List;

/**
 * @author harry
 * @version 1.0
 * @title: UsernameValidator
 * @description: TODO
 * @date 2019/5/26 15:35
 */
public class UsernameValidator implements ExcelValidator {

    final List<String> ERROR_USERNAME_LIST = Lists.newArrayList(
            "admin", "root", "master", "administrator", "sb"
    );

    @Override
    public String valid(Object value) {
        if (value == null) {
            return "用户名不能为空";
        }
        String username = value.toString();
        if (username.length() > 12) {
            return "用户名最大长度为12";
        }
        if (ERROR_USERNAME_LIST.contains(username)) {
            return "用户名非法，不允许使用。";
        }
        return null;
    }
}
