package com.company.util;


/**
 * 
 * @author lihome
 * 
 * @see org.apache.commons.lang3.BooleanUtils
 * 
 */
public class BooleanUtil extends org.apache.commons.lang3.BooleanUtils {

    
    public static boolean getBoolean(Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        
        return "true".equals(value);
    }
}