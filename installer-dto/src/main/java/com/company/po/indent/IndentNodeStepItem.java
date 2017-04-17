package com.company.po.indent;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.company.api.fw.EnumCodes;
import com.company.po.fs.FileIndex;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.company.api.fw.TransformField;
import com.company.po.base.OrderPo;
/**
 * INDENT NODE STEP ITEM
 * 订单节点步骤详情/描述
 * @author kouwl
 *
 * @Date 2016年2月26日
 */
@Entity
@Table(name = "ZL_INDENT_NODE_STEP_ITEM")
public class IndentNodeStepItem extends OrderPo {

    private static final long serialVersionUID = 1L;

    // 订单节点步骤子项描述
    @JsonProperty
    @Column(name = "DESC1", length = 255, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String desc1;

    // 订单订单节点ID
    @JsonProperty
    @Column(name = "INDENT_NODE_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String indentNodeId;

    // 订单节点步骤ID
    @JsonProperty
    @Column(name = "INDENT_NODE_STEP_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String indentNodeStepId;
    
    // 节点步骤详情ID
    @JsonProperty
    @Column(name = "NODE_STEP_ITEM_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String nodeStepItemId;
    
    //通用状态
    @TransformField(groupCode = EnumCodes.INDENT_NODE_STEP_STATUS)
    @Column(name = "STATUS", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String status;
    
    @Transient
    private String statusDisp;
    
    @TransformField(groupCode = EnumCodes.SCNODE_CONFIRM_TYPE)
    @Column(name = "ITEM_TYPE", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String itemType;
    
    @Transient
    private String itemTypeDisp;
    
    @Transient
    private List<FileIndex> imgUrlList;
    
    @Transient
    private List<FileIndex> sketchImgUrlList;

    // 订单节点步骤
    @JsonProperty
    @Transient
    private IndentNodeStep indentNodeStep;
    
    @Column(name = "MIN_PHOTO", columnDefinition = "INT DEFAULT 0")
    private Integer minPhoto;
    
    // 示意图
    @Column(name = "IS_SKETCH", columnDefinition = "INT DEFAULT 0")
    private Integer isSketch;
    
    public Integer getMinPhoto() {
        return minPhoto;
    }

    public void setMinPhoto(Integer minPhoto) {
        this.minPhoto = minPhoto;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getIndentNodeId() {
        return indentNodeId;
    }

    public void setIndentNodeId(String indentNodeId) {
        this.indentNodeId = indentNodeId;
    }

    public String getIndentNodeStepId() {
        return indentNodeStepId;
    }

    public void setIndentNodeStepId(String indentNodeStepId) {
        this.indentNodeStepId = indentNodeStepId;
    }

    public String getNodeStepItemId() {
        return nodeStepItemId;
    }

    public void setNodeStepItemId(String nodeStepItemId) {
        this.nodeStepItemId = nodeStepItemId;
    }

    public IndentNodeStep getIndentNodeStep() {
        return indentNodeStep;
    }

    public void setIndentNodeStep(IndentNodeStep indentNodeStep) {
        this.indentNodeStep = indentNodeStep;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemTypeDisp() {
        return itemTypeDisp;
    }

    public void setItemTypeDisp(String itemTypeDisp) {
        this.itemTypeDisp = itemTypeDisp;
    }

    public List<FileIndex> getImgUrlList() {
        return imgUrlList;
    }

    public List<FileIndex> getSketchImgUrlList() {
        return sketchImgUrlList;
    }

    public void setImgUrlList(List<FileIndex> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

    public void setSketchImgUrlList(List<FileIndex> sketchImgUrlList) {
        this.sketchImgUrlList = sketchImgUrlList;
    }

    public String getStatusDisp() {
        return statusDisp;
    }

    public void setStatusDisp(String statusDisp) {
        this.statusDisp = statusDisp;
    }

    public Integer getIsSketch() {
        return isSketch;
    }

    public void setIsSketch(Integer isSketch) {
        this.isSketch = isSketch;
    }

}
