package com.qcz.qmplatform.common.cache;

import cn.hutool.cache.impl.CacheObj;
import cn.hutool.cache.impl.WeakCache;
import cn.hutool.core.lang.mutable.Mutable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MyWeakCache<K, V> extends WeakCache<K, V> {

    public MyWeakCache(long timeout) {
        super(timeout);
    }

    public Set<K> getKeys() {
        Set<K> set = new HashSet<>();
        Set<Mutable<K>> mutableSet = cacheMap.keySet();
        for (Mutable<K> kMutable : mutableSet) {
            set.add(kMutable.get());
        }
        return set;
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
