package com.company.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据传送对象(Data Transfer Object)
 * 
 * @author lihome
 *
 * @see java.util.HashMap
 */
public class Dto extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public Dto() {
        super();
    }

    public Dto(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public Dto(int initialCapacity) {
        super(initialCapacity);
    }

    public Dto(Map<? extends String, ? extends Object> m) {
        super(m);
    }

    public Object add(String key, Object value) {
        return super.put(key, value);
    }

    public Object addProperty(String key, Object value) {
        return super.put(key, value);
    }

    public String getAsString(String key) {
        return ObjectUtil.toString(this.get(key));
    }
    
    public boolean getAsBoolean(String key) {
        return BooleanUtil.getBoolean(this.get(key));
    }

    public int getAsInt(String key) {
        Object value = this.get(key);
        if (value instanceof Integer)
            return (Integer) value;
        return NumberUtil.toInt(ObjectUtil.toString(value));
    }

    public double getAsDouble(String key) {
        Object value = this.get(key);
        if (value instanceof Number)
            return ((Number) value).doubleValue();
        return NumberUtil.toDouble(ObjectUtil.toString(value));
    }

    public long getAsLong(String key) {
        Object value = this.get(key);
        if (value instanceof Number)
            return ((Number) value).longValue();
        return NumberUtil.toLong(ObjectUtil.toString(value));
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getAsList(String key) {
        return (List<T>) get(key);
    }
}