package com.qcz.qmplatform.common.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroCacheManager implements CacheManager {

    @Autowired
    private ShiroCache shiroCache;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return shiroCache;
    }
}
