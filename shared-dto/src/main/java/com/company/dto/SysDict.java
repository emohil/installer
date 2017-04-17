package com.company.dto;

import java.io.Serializable;

public class SysDict implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final SysDict ALL = new SysDict("", "--全部 --", 0);
    public static final SysDict EMPTY = new SysDict("", "", 0);
    public static final SysDict CHECK = new SysDict("", "--请选择--", 0);

    private String value;
    private String text;
    private int orders;

    public SysDict() {
    }

    public SysDict(String value, String text, int orders) {
        super();
        this.value = value;
        this.text = text;
        this.orders = orders;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }
}