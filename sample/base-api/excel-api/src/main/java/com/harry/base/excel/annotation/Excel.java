package com.harry.base.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author harry
 * @version 1.0
 * @title: Excel
 * @description: Excel mapping对象注解
 * @date 2019/5/26 13:56
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Excel {

    String value() default "";
}
