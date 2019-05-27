package com.harry.base.excel.validator;

/**
 * @author harry
 * @version 1.0
 * @title: ExcelValidator
 * @description: 验证单元格的值, 若验证失败, 请返回错误消息.
 * @date 2019/5/26 14:04
 */
public interface ExcelValidator {

    /**
     *
     * @param value 当前单元格的值
     * @return null or errorMessage
     */
    String valid(Object value);

}
