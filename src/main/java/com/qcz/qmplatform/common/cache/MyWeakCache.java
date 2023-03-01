package com.qcz.qmplatform.common.cache;

import cn.hutool.cache.impl.CacheObj;
import cn.hutool.cache.impl.WeakCache;
import cn.hutool.core.lang.mutable.Mutable;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class MyWeakCache<K, V> extends WeakCache<K, V> {

    public MyWeakCache(long timeout) {
        super(timeout);
    }

    public Set<K> getKeys() {
        return cacheMap.keySet().stream().map(Mutable::get).collect(Collectors.toSet());
    }

    public Collection<V> getValues() {
        return cacheMap.values().stream().map(CacheObj::getValue).collect(Collectors.toList());
    }
}
