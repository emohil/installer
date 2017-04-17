package com.company.po.scnode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.TransformField;
import com.company.po.base.OrderPo;

/**
 * 节点步骤子项/详情
 * 
 * @author lihome
 *
 */
@Entity
@Table(name = "ZL_SCNODE_STEP_ITEM")
public class ScnodeStepItem extends OrderPo {

    private static final long serialVersionUID = 1L;

    // 节点步骤子项描述
    @Column(name = "NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String name1;

    // 节点步骤子项描述
    @Column(name = "DESC1", length = 255, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String desc1;

    // 节点ID
    @Column(name = "SCNODE_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String scnodeId;

    // 节点步骤ID
    @Column(name = "SCNODE_STEP_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String scnodeStepId;

    // 节点步骤子项的类型，目前只有确认类别的才需要此字段
    @TransformField(groupCode = EnumCodes.SCNODE_CONFIRM_TYPE)
    @Column(name = "ITEM_TYPE", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String itemType;

    @Transient
    private String itemTypeDisp;

    @Column(name = "MIN_PHOTO", columnDefinition = "INT DEFAULT 0")
    private Integer minPhoto;

    // 示意图
    @Column(name = "IS_SKETCH", columnDefinition = "INT DEFAULT 0")
    private Integer isSketch;

    @Transient
    private ScnodeStep scnodeStep;

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getScnodeId() {
        return scnodeId;
    }

    public void setScnodeId(String scnodeId) {
        this.scnodeId = scnodeId;
    }

    public String getScnodeStepId() {
        return scnodeStepId;
    }

    public void setScnodeStepId(String scnodeStepId) {
        this.scnodeStepId = scnodeStepId;
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

    public Integer getMinPhoto() {
        return minPhoto;
    }

    public void setMinPhoto(Integer minPhoto) {
        this.minPhoto = minPhoto;
    }

    public Integer getIsSketch() {
        return isSketch;
    }

    public void setIsSketch(Integer isSketch) {
        this.isSketch = isSketch;
    }

    public ScnodeStep getScnodeStep() {
        return scnodeStep;
    }

    public void setScnodeStep(ScnodeStep scnodeStep) {
        this.scnodeStep = scnodeStep;
    }
}