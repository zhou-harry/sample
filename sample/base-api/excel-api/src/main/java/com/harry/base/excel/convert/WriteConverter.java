package com.harry.base.excel.convert;

import com.harry.base.excel.exception.ExcelWriteConverterException;

/**
 * @author harry
 * @version 1.0
 * @title: WriteConverter
 * @description: 将value转换成指定的值, 用于写入excel表格中
 * @date 2019/5/26 14:12
 */
public interface WriteConverter {
    /**
     * @param value 当前单元格的值
     * @return 转换后的值
     */
    String convert(Object value) throws ExcelWriteConverterException;
}
