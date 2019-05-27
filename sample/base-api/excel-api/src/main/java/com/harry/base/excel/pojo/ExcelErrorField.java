package com.harry.base.excel.pojo;

import lombok.*;

/**
 * @author harry
 * @version 1.0
 * @title: ExcelErrorField
 * @description: TODO
 * @date 2019/5/26 14:18
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExcelErrorField {

    private Integer cellIndex;
    private String name;
    private String column;
    private String errorMessage;
}
