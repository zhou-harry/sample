package com.harry.sample.client.excel.convert;

import com.harry.base.excel.convert.ReadConverter;
import com.harry.base.excel.exception.ExcelKitReadConverterException;

/**
 * @author harry
 * @version 1.0
 * @title: CustomizeFieldReadConverter
 * @description: TODO
 * @date 2019/5/26 15:54
 */
public class CustomizeFieldReadConverter implements ReadConverter {
    /**
     * 读取单元格时，将值进行转换（此处示例为计算单元格字符串char的总和）
     * @param value 当前单元格的值
     * @return
     * @throws ExcelKitReadConverterException
     */
    @Override
    public Object convert(Object value) throws ExcelKitReadConverterException {
        if (value == null) {
            return value;
        }

        int convertedValue = 0;
        for (char c : value.toString().toCharArray()) {
            convertedValue += Integer.valueOf(c);
        }
        return convertedValue;
    }
}
