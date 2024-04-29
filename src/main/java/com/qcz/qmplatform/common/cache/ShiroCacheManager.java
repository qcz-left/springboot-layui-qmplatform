package com.qcz.qmplatform.common.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import jakarta.annotation.Resource;

public class ShiroCacheManager implements CacheManager {

    @Resource
    private ShiroCache shiroCache;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return shiroCache;
    }
}
