package com.harry.base.excel.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.lang.reflect.InvocationTargetException;

/**
 * @author harry
 * @version 1.0
 * @title: BeanUtil
 * @description: TODO
 * @date 2019/5/26 14:42
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanUtil extends org.apache.commons.beanutils.BeanUtils {

    public static void setComplexProperty(Object bean, String name, Object value)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        if (!name.contains(".")) {
            BeanUtil.setProperty(bean, name, value);
            return;
        }
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        String[] propertyLevels = name.split("\\.");
        String parentPropertyName = "";
        for (int i = 0; i < propertyLevels.length; i++) {
            String p = propertyLevels[i];
            parentPropertyName = (parentPropertyName.equals("") ? p : parentPropertyName + "." + p);
            if (i < (propertyLevels.length - 1) &&
                    propertyUtilsBean.getProperty(bean, parentPropertyName) != null) {
                continue;
            }
            Class<?> parentClass = propertyUtilsBean.getPropertyType(bean, parentPropertyName);
            if (i < (propertyLevels.length - 1)) {
                BeanUtil.setProperty(bean,
                        parentPropertyName, parentClass.getConstructor().newInstance());
            } else {
                BeanUtil.setProperty(bean, parentPropertyName,
                        parentClass.getConstructor(String.class).newInstance(value));
            }
        }
    }
}
