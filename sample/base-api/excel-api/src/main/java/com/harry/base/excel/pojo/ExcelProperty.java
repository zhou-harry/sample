package com.harry.base.excel.pojo;

import com.harry.base.excel.convert.ReadConverter;
import com.harry.base.excel.convert.WriteConverter;
import com.harry.base.excel.repository.ExcelRepository;
import com.harry.base.excel.validator.ExcelValidator;
import lombok.*;

/**
 * @author harry
 * @version 1.0
 * @title: ExcelProperty
 * @description: TODO
 * @date 2019/5/26 14:16
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExcelProperty {

    private String name;
    private String column;
    private Boolean required;
    private Short width;
    private String comment;
    private Integer maxLength;
    private String dateFormat;
    private ExcelRepository repository;
    private String writeConverterExp;
    private WriteConverter writeConverter;
    private String readConverterExp;
    private ReadConverter readConverter;
    private String regularExp;
    private String regularExpMessage;
    private ExcelValidator validator;

}
