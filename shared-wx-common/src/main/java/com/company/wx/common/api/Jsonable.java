package com.company.wx.common.api;

/**
 * Json able of a class is enabled by the class implementing the Jsonable
 * interface.
 * 
 * @author lihome
 *
 */
public interface Jsonable {

    /**
     * to json string.
     * @return
     */
    String toJson();
}