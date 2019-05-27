package com.harry.base.excel.pojo;

import lombok.*;

import java.util.List;

/**
 * @author harry
 * @version 1.0
 * @title: ExcelMapping
 * @description: TODO
 * @date 2019/5/26 14:16
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExcelMapping {

    private String name;

    private List<ExcelProperty> propertyList;
}
