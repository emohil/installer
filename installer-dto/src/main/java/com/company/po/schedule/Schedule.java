package com.company.po.schedule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.TransformField;
import com.company.po.base.StringIdPo;

@Entity
@Table(name = "ZL_SCHEDULE")
public class Schedule extends StringIdPo {

    private static final long serialVersionUID = 1L;

    /**
     * 计划名称
     */
    @Column(name = "NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String name1;

    /**
     * 全限定类名
     */
    @Column(name = "QUALIFIED_NAME", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String qualifiedName;

    /**
     * 计划状态
     */
    @TransformField(groupCode = EnumCodes.ENABLE_STATUS)
    @Column(name = "STATUS", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String status;

    @Transient
    private String statusDisp;

    /**
     * 执行表达式
     */
    @Column(name = "CRON_EXPRESSION", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String cronExpression;

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatusDisp() {
        return statusDisp;
    }

    public void setStatusDisp(String statusDisp) {
        this.statusDisp = statusDisp;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}
