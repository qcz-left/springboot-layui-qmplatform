package com.qcz.qmplatform.common.utils;

import cn.hutool.core.collection.CollectionUtil;

import java.util.List;
import java.util.function.Function;

/**
 * 集合工具类
 */
public class CollectionUtils extends CollectionUtil {

    /**
     * 通过func自定义一个规则，此规则将原集合中的元素转换成新的元素，生成新的列表返回，忽略null值<br>
     * 例如提供的是一个Bean列表，通过Function接口实现获取某个字段值，返回这个字段值组成的新列表
     *
     * @param <T>        集合元素类型
     * @param <R>        返回集合元素类型
     * @param collection 原集合
     * @param func       编辑函数
     * @return 抽取后的新列表
     */
    public static <T, R> List<R> map(Iterable<T> collection, Function<? super T, ? extends R> func) {
        return map(collection, func, true);
    }

}
