package com.qcz.qmplatform.common.cache;

import cn.hutool.cache.impl.CacheObj;
import cn.hutool.cache.impl.WeakCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class MyWeakCache<K, V> extends WeakCache<K, V> {

    public MyWeakCache(long timeout) {
        super(timeout);
    }

    public Set<K> getKeys() {
        return cacheMap.keySet();
    }

    public Collection<V> getValues() {
        Collection<V> values = new ArrayList<>();
        Collection<CacheObj<K, V>> cacheObjs = cacheMap.values();
        for (CacheObj<K, V> cacheObj : cacheObjs) {
            values.add(cacheObj.getValue());
        }
        return values;
    }
}
