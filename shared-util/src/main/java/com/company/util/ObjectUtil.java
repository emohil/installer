package com.company.util;

/**
 * 
 * @author lihome
 * 
 * @see org.apache.commons.lang3.ObjectUtils
 *
 */
public class ObjectUtil extends org.apache.commons.lang3.ObjectUtils {

    public static String toString(final Object obj) {
        return toString(obj, StringUtil.EMPTY);
    }

    public static String toString(final Object o, String nullDefault) {

        return o != null ? o.toString() : nullDefault;
    }
}
