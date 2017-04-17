package com.company.dto.city;

import java.util.List;

public class CityData {

    //value
    private String v;

    //name
    private String n;
    
    //parent
    private String p;

    //children
    private List<CityData> c;

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public List<CityData> getC() {
        return c;
    }

    public void setC(List<CityData> c) {
        this.c = c;
    }
}