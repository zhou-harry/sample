package com.harry.base.excel.convert;

import com.harry.base.excel.exception.ExcelKitReadConverterException;

/**
 * @author harry
 * @version 1.0
 * @title: ReadConverter
 * @description: 将value转换成指定的值, 读取时映射到实体中
 * @date 2019/5/26 14:06
 */
public interface ReadConverter {

    /**
     * @param value 当前单元格的值
     * @return 转换后的值
     */
    Object convert(Object value) throws ExcelKitReadConverterException;

}
