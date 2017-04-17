package com.company.dto.indentnode;

import java.io.Serializable;
import java.util.List;

import com.company.api.fw.EnumCodes;
import com.company.po.indent.IndentException;
import com.company.api.fw.TransformField;

public class IndentNodeTreeItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String pid;

    private String code1;

    private String name1;
    
    @TransformField(groupCode = EnumCodes.SCNODE_STEP_TYPE)
    private String stepType;

    private String stepTypeDisp;
    
    private String result;
    
    private String stepStatus;
    
    private String stepStatusDisp;
    
    private List<IndentNodeTreeItem> children;
    
    private List<IndentException> iEList;

    public List<IndentException> getiEList() {
        return iEList;
    }

    public void setiEList(List<IndentException> iEList) {
        this.iEList = iEList;
    }

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

    public String getStepType() {
        return stepType;
    }

    public void setStepType(String stepType) {
        this.stepType = stepType;
    }

    public String getStepTypeDisp() {
        return stepTypeDisp;
    }

    public void setStepTypeDisp(String stepTypeDisp) {
        this.stepTypeDisp = stepTypeDisp;
    }

    public List<IndentNodeTreeItem> getChildren() {
        return children;
    }

    public void setChildren(List<IndentNodeTreeItem> children) {
        this.children = children;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStepStatus() {
        return stepStatus;
    }

    public void setStepStatus(String stepStatus) {
        this.stepStatus = stepStatus;
    }

    public String getStepStatusDisp() {
        return stepStatusDisp;
    }

    public void setStepStatusDisp(String stepStatusDisp) {
        this.stepStatusDisp = stepStatusDisp;
    }

}