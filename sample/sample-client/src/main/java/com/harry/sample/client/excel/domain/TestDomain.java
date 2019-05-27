package com.harry.sample.client.excel.domain;

import com.harry.base.excel.annotation.Excel;
import com.harry.base.excel.annotation.ExcelField;
import com.harry.base.excel.validator.EmailValidator;
import com.harry.sample.client.excel.convert.CustomizeFieldReadConverter;
import com.harry.sample.client.excel.convert.CustomizeFieldWriteConverter;
import com.harry.sample.client.excel.repository.GroupNameRepository;
import com.harry.sample.client.excel.repository.SexRepository;
import com.harry.sample.client.excel.repository.UsernameRepository;
import com.harry.sample.client.excel.validate.UsernameValidator;
import lombok.*;

import java.util.Date;

/**
 * @author harry
 * @version 1.0
 * @title: TestDomain
 * @description: TODO
 * @date 2019/5/26 15:31
 */
@Getter
@Setter
@ToString
@Excel("测试用户")
@NoArgsConstructor
@AllArgsConstructor
public class TestDomain {

    @ExcelField(value = "编号", width = 30)
    private Integer userId;

    @ExcelField(
            value = "用户名",
            required = true,
            validator = UsernameValidator.class,
            repository = UsernameRepository.class,
            comment = "请填写用户名，最大长度为12，且不能重复"
    )
    private String username;

    @ExcelField(value = "密码", required = true, maxLength = 8)
    private String password;

    @ExcelField(value = "邮箱", validator = EmailValidator.class)
    private String email;

    @ExcelField(
            value = "性别",
            readConverterExp = "未知=0,男=1,女=2",
            writeConverterExp = "0=未知,1=男,2=女",
            repository = SexRepository.class
    )
    private Integer sex;

    @ExcelField(
            value = "用户组",
            name = "group.name",
            repository = GroupNameRepository.class
    )
    private TestGroupDomain group;

    @ExcelField(value = "创建时间", dateFormat = "yyyy/MM/dd HH:mm:ss")
    private Date createAt;

    @ExcelField(
            value = "自定义字段",
            maxLength = 5,
            comment = "可以乱填，但是长度不能超过5，导入时最终会转换为数字",
            writeConverter = CustomizeFieldWriteConverter.class,// 写文件时，将数字转字符串
            readConverter = CustomizeFieldReadConverter.class// 读文件时，将字符串转数字
    )
    private Integer customizeField;

}
