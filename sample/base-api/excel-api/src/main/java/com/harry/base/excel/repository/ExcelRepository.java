package com.harry.base.excel.repository;

/**
 * @author harry
 * @version 1.0
 * @title: ExcelRepository
 * @description: 指定excel单元格的下拉框数据源, 用于规范生成Excel模板的数据校验
 * @date 2019/5/26 14:00
 */
public interface ExcelRepository {

    String[] resource();

}
