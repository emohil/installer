package com.company.dto;

public class ValueTextPair {

    private String value;
    private String value2;
    private String text;

    public ValueTextPair() {
    }

    public ValueTextPair(String value, String text) {
        super();
        this.value = value;
        this.text = text;
    }

    public ValueTextPair(String value, String value2, String text) {
        super();
        this.value = value;
        this.value2 = value2;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}