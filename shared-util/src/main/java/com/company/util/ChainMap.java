package com.company.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 一个支持链操作(函数返回this)的Map，本身也会保持放入顺序 
 * 
 * @param <K>
 * @param <V>
 */
public class ChainMap<K, V> extends LinkedHashMap<K, V> {

    private static final long serialVersionUID = 1L;

    public ChainMap<K, V> add(K key, V value) {
        put(key, value);
        return this;
    }

    public ChainMap() {
        super();
    }

    public ChainMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public ChainMap(int initialCapacity) {
        super(initialCapacity);
    }

    public ChainMap(Map<? extends K, ? extends V> m) {
        super(m);
    }
}