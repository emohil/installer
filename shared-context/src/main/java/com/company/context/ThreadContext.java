package com.company.context;

import com.company.context.core.ThreadLocalHelper;


public class ThreadContext {

    private static ThreadLocal<ContextBean<String>> tl = ThreadLocalHelper.registStatic(new ThreadLocal<ContextBean<String>>());

    public static ContextBean<String> getContextBean() {
        return tl.get();
    }
    
    public static void setContextBean(ContextBean<String> bean) {
        tl.set(bean);
    }
    
    public static boolean isReady() {
        return getContextBean() != null;
    }
    
    
    public static String getKeyId() {
        ContextBean<String> cb = getContextBean();
        return cb == null ? null : cb.getKeyId();
    }
    
    public static void clearContext() {
        tl.remove();
    }
}