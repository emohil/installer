package com.company.wx.common.api;

import com.company.util.json.JacksonHelper;

/**
 * Jsonable Bean abstract super class.
 * @author lihome
 *
 * @param <T> Bean Class
 */
public abstract class JsonBean<T> implements Jsonable {

    /*
     * (non-Javadoc)
     * @see com.company.wx.common.api.Jsonable#toJson()
     */
    @Override
    public String toJson() {
        return JacksonHelper.toJson(this);
    }

    /**
     * Override Object toString() method.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + toJson();
    }

    /**
     * Convert a json string to Bean class.
     * @param json
     * @param clazz
     * @return
     */
    protected static <T> T fromJson(String json, Class<T> clazz) {
        return (T) JacksonHelper.toObject(json, clazz);
    }
}