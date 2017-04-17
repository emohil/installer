package com.company.dto.scnode;

import java.io.Serializable;
import java.util.List;

import com.company.api.fw.DictCodes;
import com.company.api.fw.TransformField;

public class ScnodeTreeItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String pid;

    private String code1;

    private String name1;

    private Integer orders;
    /**
     * 类型： EnumCodes.SCNODE_STEP_TYPE and EnumCodes.SCNODE_CONFIRM_TYPE
     */
    private String type;

    private String typeDisp;
    
    @TransformField(groupCode = DictCodes.SERVICE_SORT)
    private String sortId;

    private String sortIdDisp;
    
    private List<ScnodeTreeItem> children;

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

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDisp() {
        return typeDisp;
    }

    public void setTypeDisp(String typeDisp) {
        this.typeDisp = typeDisp;
    }

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }

    public String getSortIdDisp() {
        return sortIdDisp;
    }

    public void setSortIdDisp(String sortIdDisp) {
        this.sortIdDisp = sortIdDisp;
    }

    public List<ScnodeTreeItem> getChildren() {
        return children;
    }

    public void setChildren(List<ScnodeTreeItem> children) {
        this.children = children;
    }
}