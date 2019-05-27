package com.harry.base.excel.handler;

import com.harry.base.excel.pojo.ExcelErrorField;

import java.util.List;

/**
 * @author harry
 * @version 1.0
 * @title: ExcelReadHandler
 * @description: TODO
 * @date 2019/5/26 15:04
 */
public interface ExcelReadHandler<T> {

    void onSuccess(int sheetIndex, int rowIndex, T entity);

    void onError(int sheetIndex, int rowIndex, List<ExcelErrorField> errorFields);
}
