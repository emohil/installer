package com.company.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 辅助产生generic collection对象 
 * 
 * @author lihome
 *
 */
public class New {
    
    public static <K, V> ChainMap<K, V> chainMap() {
        return new ChainMap<K, V>();
    }

    public static <K, V> ChainMap<K, V> chainMap(Map<? extends K, ? extends V> m) {
        return new ChainMap<K, V>(m);
    }

    public static <K, V> ConcurrentHashMap<K, V> concurrentMap() {
        return new ConcurrentHashMap<K, V>();
    }

    public static <K, V> ConcurrentHashMap<K, V> concurrentMap(Map<? extends K, ? extends V> m) {
        return new ConcurrentHashMap<K, V>(m);
    }

    public static <K, V> HashMap<K, V> hashMap() {
        return new HashMap<K, V>();
    }

    public static <K, V> HashMap<K, V> hashMap(Map<? extends K, ? extends V> m) {
        return new HashMap<K, V>(m);
    }

    public static <T> Set<T> hashSet() {
        return new HashSet<T>();
    }

    public static <T> Set<T> hashSet(Collection<? extends T> c) {
        return new HashSet<T>(c);
    }

    public static <K, V> LinkedHashMap<K, V> linkedMap() {
        return new LinkedHashMap<K, V>();
    }

    public static <K, V> LinkedHashMap<K, V> linkedMap(Map<? extends K, ? extends V> m) {
        return new LinkedHashMap<K, V>(m);
    }

    public static <T> LinkedHashSet<T> linkedSet() {
        return new LinkedHashSet<T>();
    }

    public static <T> LinkedHashSet<T> linkedSet(Collection<? extends T> c) {
        return new LinkedHashSet<T>(c);
    }

    public static <T> List<T> list() {
        return new ArrayList<T>();
    }

    public static <T> List<T> list(Collection<? extends T> objs) {
        return new ArrayList<T>(objs);
    }

    public static <T> List<T> list(int initialCapacity) {
        return new ArrayList<T>(initialCapacity);
    }

    @SafeVarargs
    public static <T> List<T> list(T... objs) {
        return objs == null || objs.length == 0 ? new ArrayList<T>() : new ArrayList<T>(Arrays.asList(objs));
    }
}
