package com.company.dto.sctype;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.company.api.fw.DictCodes;
import com.company.api.fw.TransformField;

public class SctypeTreeItem implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String id;
    
    private String pid;
    
    private String code1;
    
    private String name1;
    
    @TransformField(groupCode = DictCodes.UOM)
    private String unit;
    
    private String unitDisp;
    
    private BigDecimal baseQuote;
    
    private List<SctypeTreeItem> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitDisp() {
        return unitDisp;
    }

    public void setUnitDisp(String unitDisp) {
        this.unitDisp = unitDisp;
    }

    public BigDecimal getBaseQuote() {
        return baseQuote;
    }

    public void setBaseQuote(BigDecimal baseQuote) {
        this.baseQuote = baseQuote;
    }

    public List<SctypeTreeItem> getChildren() {
        return children;
    }

    public void setChildren(List<SctypeTreeItem> children) {
        this.children = children;
    }
}