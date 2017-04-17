package com.company.wx.mp.bean;

import java.io.Serializable;

import com.company.util.json.JacksonHelper;

/**
 * 微信用户分组
 * 
 * @author chanjarster
 *
 */
public class WxMpGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id = -1;
    private String name;
    private long count;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public static WxMpGroup fromJson(String json) {
        return JacksonHelper.toObject(json, WxMpGroup.class);
    }

    public String toJson() {
        return JacksonHelper.toJson(this);
    }

    @Override
    public String toString() {
        return "WxMpGroup [id=" + id + ", name=" + name + ", count=" + count + "]";
    }
}