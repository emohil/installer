package com.company.po.indent;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.company.api.fw.DictCodes;
import com.company.api.fw.EnumCodes;
import com.company.po.fs.FileIndex;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.company.api.fw.TransformField;
import com.company.po.base.OrderPo;
/**
 * INDENT NODE STEP
 * 订单节点步骤
 * @author kouwl
 *
 * @Date 2016年2月26日
 */
@Entity
@Table(name = "ZL_INDENT_NODE_STEP")
public class IndentNodeStep extends OrderPo {

    private static final long serialVersionUID = 1L;

    // 订单节点ID
    @JsonProperty
    @Column(name = "INDENT_NODE_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String indentNodeId;
    
    // 节点步骤名称
    @JsonProperty
    @Column(name = "NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String name1;
    
    //图片类型的名字
    @Column(name = "PHOTO_NAME", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String photoName;
    
    // 节点步骤描述
    @JsonProperty
    @Column(name = "DESC1", length = 255, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String desc1;
    
    // 节点步骤的类型
    @JsonProperty
    @TransformField(groupCode = EnumCodes.SCNODE_STEP_TYPE)
    @Column(name = "STEP_TYPE", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String stepType;
    
    @Transient
    @JsonProperty
    private String stepTypeDisp;
    
    //节点步骤状态
    @JsonProperty
    @TransformField(groupCode = EnumCodes.INDENT_NODE_STEP_STATUS)
    @Column(name = "NODE_STEP_STATUS", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String nodeStepStatus;
    
    @Transient
    @JsonProperty
    private String nodeStepStatusDisp;
    
    //服务类别
    @TransformField(groupCode = DictCodes.SERVICE_SORT)
    @Column(name = "SORT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String sortId;
    
    @Transient
    private String sortIdDisp;
    
    @Transient
    @JsonProperty
    private IndentNode indentNode;
    
    @Transient
    @JsonProperty
    private List<IndentNodeStepItem> indentNodeStepItemList;
    
    @Transient
    private List<FileIndex> fileIndexList;

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

    public String getIndentNodeId() {
        return indentNodeId;
    }

    public void setIndentNodeId(String indentNodeId) {
        this.indentNodeId = indentNodeId;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
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

    public IndentNode getIndentNode() {
        return indentNode;
    }

    public void setIndentNode(IndentNode indentNode) {
        this.indentNode = indentNode;
    }

    public List<IndentNodeStepItem> getIndentNodeStepItemList() {
        return indentNodeStepItemList;
    }

    public void setIndentNodeStepItemList(List<IndentNodeStepItem> indentNodeStepItemList) {
        this.indentNodeStepItemList = indentNodeStepItemList;
    }

    public String getNodeStepStatus() {
        return nodeStepStatus;
    }

    public void setNodeStepStatus(String nodeStepStatus) {
        this.nodeStepStatus = nodeStepStatus;
    }

    public String getNodeStepStatusDisp() {
        return nodeStepStatusDisp;
    }

    public void setNodeStepStatusDisp(String nodeStepStatusDisp) {
        this.nodeStepStatusDisp = nodeStepStatusDisp;
    }

    public List<FileIndex> getFileIndexList() {
        return fileIndexList;
    }

    public void setFileIndexList(List<FileIndex> fileIndexList) {
        this.fileIndexList = fileIndexList;
    }
    
}
