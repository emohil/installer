package com.company.api.fw.service.impl;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;


public class TransformFieldAspect {
    
    private static final String[] TRANSFORM_METHOD = {
        "list", "find"
    };
    
    @Autowired
    private TransformFieldExecutor executor;

    @SuppressWarnings("unchecked")
    public Object autoComplete(JoinPoint jp, Object retVal) throws Throwable {
        if (retVal == null) {
            return retVal;
        }
        
        String methodName = jp.getSignature().getName();
        
        if (needTransform(methodName)) {
            if (retVal instanceof Iterable<?>) {
                executor.transforms((Iterable<? extends Object>) retVal);
            } else {
                executor.transform(retVal);
            }
        }
        
        return retVal;
    }
    
    private boolean needTransform(String name) {
        for (String method : TRANSFORM_METHOD) {
            if (name.toLowerCase().contains(method)) {
                return true;
            }
        }
        return false;
    }
}
