package com.xxf.common.utils;

import java.lang.reflect.Field;

/**
 * @Description 日期工具类
 * @Date Created in 2020/3/19 11:26
 * @User xxf
 */
public class ReflectUtils {
    /**
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param object
     * @return
     */
    public static Object getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            return null;
        }

    }
}
