package com.harry.sample.client.excel.convert;

import com.harry.base.excel.convert.WriteConverter;
import com.harry.base.excel.exception.ExcelWriteConverterException;

/**
 * @author harry
 * @version 1.0
 * @title: CustomizeFieldWriteConverter
 * @description: TODO
 * @date 2019/5/26 15:53
 */
public class CustomizeFieldWriteConverter implements WriteConverter {
    /**
     * 写文件时，将值进行转换（此处示例为将数值拼接为指定格式的字符串）
     *
     * @param value 当前单元格的值
     * @return
     * @throws ExcelWriteConverterException
     */
    @Override
    public String convert(Object value) throws ExcelWriteConverterException {
        return ("ID_" + value);
    }
}
