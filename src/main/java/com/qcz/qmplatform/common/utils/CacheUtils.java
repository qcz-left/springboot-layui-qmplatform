package com.qcz.qmplatform.common.utils;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.LFUCache;

public class CacheUtils {

    /**
     * 公用缓存，30分钟,100个容量，最少使用策略
     */
    private static final LFUCache<String, String> COMMON_CACHE = CacheUtil.newLFUCache(100);

    public static String get(String key) {
        return COMMON_CACHE.get(key);
    }

    /**
     * 设置缓存
     *
     * @param key     缓存标识
     * @param value   缓存值
     * @param timeout 超时时间（ms）
     */
    public static void put(String key, String value, long timeout) {
        COMMON_CACHE.put(key, value, timeout);
    }

    public static void put(String key, String value) {
        COMMON_CACHE.put(key, value);
    }
}
