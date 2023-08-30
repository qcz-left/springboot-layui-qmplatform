package com.qcz.qmplatform.common.utils;

import cn.hutool.core.util.ClassUtil;

public class ClassUtils extends ClassUtil {

    /**
     * 是否String类型或基本数据类型及其包装类
     */
    public static boolean isCommonDataType(Class<?> clazz) {
        try {
            return clazz == String.class || clazz.isPrimitive() || ((Class<?>) clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }
}
