package com.qcz.qmplatform.common.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
public class ShiroCache<K, V> implements Cache<K, V> {

    private final MyWeakCache<K, V> cache = new MyWeakCache<>(0L);

    @Override
    public V get(K k) throws CacheException {
        return cache.get(k);
    }

    @Override
    public V put(K k, V v) throws CacheException {
        cache.put(k, v);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        V v = get(k);
        cache.remove(k);
        return v;
    }

    @Override
    public void clear() throws CacheException {
        cache.clear();
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public Set<K> keys() {
        return cache.getKeys();
    }

    @Override
    public Collection<V> values() {
        return cache.getValues();
    }
}
