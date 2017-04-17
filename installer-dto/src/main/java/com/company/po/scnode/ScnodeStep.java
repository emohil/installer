package com.company.po.scnode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.TransformField;
import com.company.po.base.OrderPo;

/**
 * 节点步骤
 * 
 * @author lihome
 *
 */
@Entity
@Table(name = "ZL_SCNODE_STEP")
public class ScnodeStep extends OrderPo {

    private static final long serialVersionUID = 1L;

    // 节点步骤名称
    @Column(name = "NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String name1;

    // 节点步骤描述
    @Column(name = "DESC1", length = 255, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String desc1;

    // 节点ID
    @Column(name = "SCNODE_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String scnodeId;

    // 节点步骤的类型
    @TransformField(groupCode = EnumCodes.SCNODE_STEP_TYPE)
    @Column(name = "STEP_TYPE", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String stepType;
    
    @Transient
    private String stepTypeDisp;
    
    @Column(name = "SORT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String sortId;
    
    @Transient
    private String sortIdDisp;
    
    @Transient
    private Scnode scnode;

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

    public Scnode getScnode() {
        return scnode;
    }

    public void setScnode(Scnode scnode) {
        this.scnode = scnode;
    }
}