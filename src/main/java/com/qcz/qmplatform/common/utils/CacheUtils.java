package com.qcz.qmplatform.common.utils;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.LFUCache;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;
import com.qcz.qmplatform.module.system.domain.User;

public class CacheUtils {

    /**
     * 公用缓存,100个容量，最少使用策略
     */
    private static final LFUCache<String, String> COMMON_CACHE = CacheUtil.newLFUCache(100);

    /**
     * 在线用户信息缓存 userId -> com.qcz.qmplatform.module.system.domain.User
     */
    public static final TimedCache<String, User> USER_CACHE = CacheUtil.newTimedCache(DateUnit.HOUR.getMillis() * 2);

    /**
     * 会话id 对应的用户信息
     */
    public static final TimedCache<String, User> SESSION_CACHE = CacheUtil.newTimedCache(DateUnit.HOUR.getMillis() * 2);

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
