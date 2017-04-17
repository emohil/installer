package com.company.context.core;

import java.util.LinkedHashSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ThreadLocal监听器.
 * 
 * @author lihong
 */
public class ThreadLocalListener {

    private final Object obj = new Object();
    private ConcurrentHashMap<ThreadLocal<?>, Object> statics = new ConcurrentHashMap<ThreadLocal<?>, Object>();
    private ThreadLocal<LinkedHashSet<ThreadLocal<?>>> tmps = new ThreadLocal<LinkedHashSet<ThreadLocal<?>>>();

    private LinkedHashSet<ThreadLocal<?>> forTmps(boolean autoCreate) {
        LinkedHashSet<ThreadLocal<?>> ts = tmps.get();
        if (ts == null && autoCreate) {
            ts = new LinkedHashSet<ThreadLocal<?>>();
            ts.add(tmps);
            tmps.set(ts);
        }
        return ts;
    }

    /**
     * 清除全部的ThreadLocal信息.
     * 必须在dispatcher控制器的finaly中调用该函数,以清除.
     */
    public void clearContexts() {
        for (ThreadLocal<?> tl : statics.keySet().toArray(new ThreadLocal[0])) {
            tl.remove();
        }
        LinkedHashSet<ThreadLocal<?>> tmps = forTmps(false);
        if (tmps != null) {
            for (ThreadLocal<?> tl : tmps.toArray(new ThreadLocal[0])) {
                tl.remove();
            }
        }
    }

    /**
     * 注册一个全局的ThreadLocal(绑定在一个类的static属性下).
     * 
     * @param <E> 要注册的ThreadLocal对象
     * @param tl ThreadLocal对象
     * @return 返回刚才注册登记的ThreadLocal对象.
     */
    public <E> ThreadLocal<E> registStatic(ThreadLocal<E> tl) {
        statics.putIfAbsent(tl, obj);
        return tl;
    }

    /**
     * 注销一个全局的ThreadLocal.
     * 
     * @param <E> 要注销的ThreadLocal对象
     * @param tl ThreadLocal对象
     * @return 返回要注销的ThreadLocal对象
     */
    public <E> ThreadLocal<E> unregistStatic(ThreadLocal<E> tl) {
        statics.remove(tl);
        return tl;
    }

    /**
     * 注册一个请求级别的的threadLocal.
     * 
     * @param <E> 注册的ThreadLocal对象
     * @param tl ThreadLocal对象
     * @return 返回刚才注册登记的ThreadLocal对象.
     */
    public <E> ThreadLocal<E> regist(ThreadLocal<E> tl) {
        forTmps(true).add(tl);
        return tl;
    }

    /**
     * 注销一个请求级别的threadLocal,如果未曾注册,则什么都不做.
     * 
     * @param <E> 要注销的ThreadLocal对象
     * @param tl ThreadLocal对象
     * @return 返回要注销的ThreadLocal对象(无论操作是否真实起效了).
     */
    public <E> ThreadLocal<E> unregist(ThreadLocal<E> tl) {
        LinkedHashSet<ThreadLocal<?>> tmps = forTmps(false);
        if (tmps != null) {
            tmps.remove(tl);
        }
        return tl;
    }
}