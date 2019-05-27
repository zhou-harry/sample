package com.harry.base.excel.annotation;

import com.harry.base.excel.convert.ReadConverter;
import com.harry.base.excel.convert.WriteConverter;
import com.harry.base.excel.repository.ExcelRepository;
import com.harry.base.excel.validator.ExcelValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author harry
 * @version 1.0
 * @title: ExcelField
 * @description: Excel mapping属性注解
 * @date 2019/5/26 13:57
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelField {

    /**
     * @return 单元格名称(如 : id字段显示为 ' 编号 ') 默认为字段名
     */
    String value() default "";

    /**
     * 属性名, 仅在复杂数据类型时配置.
     * <pre>
     *   (At)ExcelField(name="user.name");
     *   private User user;
     * </pre>
     *
     * @return 属性名
     */
    String name() default "";

    /**
     * @return 单元格宽度[仅限表头] 默认-1(自动计算列宽)
     */
    short width() default -1;

    /**
     * @return 是否必填
     */
    boolean required() default false;

    /**
     * @return 批注信息, 生成模板时生效
     */
    String comment() default "";

    /**
     * @return 最大长度, 读取时生效, 默认不限制
     */
    int maxLength() default -1;

    /**
     * 日期格式, 如: yyyy/MM/dd
     *
     * @return 日期格式
     */
    String dateFormat() default "";

    /**
     * @return 下拉框数据源, 生成模板和验证数据时生效
     */
    Class<? extends ExcelRepository> repository() default Void.class;

    /**
     * 写入内容转换表达式 (如: 1=男,2=女), 与 writeConverter 二选一(优先级0)
     *
     * @return 写入内容转换表达式
     * @see ExcelField#writeConverter()
     */
    String writeConverterExp() default "";

    /**
     * 写入内容转换器, 与 writeConverterExp 二选一(优先级1)
     *
     * @return 写入内容转换器
     * @see ExcelField#writeConverterExp()
     */
    Class<? extends WriteConverter> writeConverter() default Void.class;

    /**
     * 读取内容转表达式 (如: 男=1,女=2), 与 readConverter 二选一(优先级0)
     *
     * @return 读取内容转表达式
     * @see ExcelField#readConverter()
     */
    String readConverterExp() default "";

    /**
     * 读取内容转换器, 与 readConverterExp 二选一(优先级1)
     *
     * @return 读取内容转换器
     * @see ExcelField#readConverterExp()
     */
    Class<? extends ReadConverter> readConverter() default Void.class;

    /**
     * 正则表达式, 读取时生效, 与 validator 二选一(优先级0)
     *
     * @return 正则表达式
     * @see ExcelField#validator()
     */
    String regularExp() default "";

    /**
     * 正则表达式验证失败时的错误消息, regularExp 配置后生效
     *
     * @return 正则表达式验证失败时的错误消息
     * @see ExcelField#regularExp()
     */
    String regularExpMessage() default "";

    /**
     * 自定义验证器, 读取时生效, 与 regularExp 二选一(优先级1)
     *
     * @return 自定义验证器
     * @see ExcelField#regularExp()
     */
    Class<? extends ExcelValidator> validator() default Void.class;

    class Void implements ExcelRepository, ReadConverter, WriteConverter, ExcelValidator {

        @Override
        public String[] resource() {
            return new String[0];
        }

        @Override
        public String convert(Object value) {
            return null;
        }

        @Override
        public String valid(Object value) {
            return null;
        }
    }
}
