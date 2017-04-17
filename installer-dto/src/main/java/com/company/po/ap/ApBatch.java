package com.company.po.ap;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.TransformField;
import com.company.po.base.StringIdPo;

/**
 * AP 结算批次表
 * 
 * @author lihome
 *
 */
@Entity
@Table(name = "ZL_AP_BATCH")
public class ApBatch extends StringIdPo {

    private static final long serialVersionUID = 1L;

    // 批次描述
    @Column(name = "DESC1", length = 255, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String desc1;

    // 批次状态
    @TransformField(groupCode = EnumCodes.BATCH_STATUS)
    @Column(name = "BATCH_STATUS", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String batchStatus;
    
    @Transient
    private String batchStatusDisp;

    // 结算开始日期
    @Temporal(TemporalType.DATE)
    @Column(name = "BEGIN_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date beginDate;

    // 结算截止日期
    @Temporal(TemporalType.DATE)
    @Column(name = "END_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date endDate;

    // 结算日期(批次日期)
    @Temporal(TemporalType.DATE)
    @Column(name = "BATCH_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date batchDate;

    // 总订单数
    @Column(name = "INDENTS", columnDefinition = "INT DEFAULT 0")
    private Integer indents;

    // 经理人数
    @Column(name = "MANAGERS", columnDefinition = "INT DEFAULT 0")
    private Integer managers;

    // 工人数
    @Column(name = "WORKERS", columnDefinition = "INT DEFAULT 0")
    private Integer workers;

    // 订单总金额
    @Column(name = "INDENT_AMT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal indentAmt;

    // 工人金额
    @Column(name = "WORKER_AMT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal workerAmt;

    // 经理人金额
    @Column(name = "MANAGER_AMT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal managerAmt;

    // 利润
    @Column(name = "PROFIT_AMT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal profitAmt;
    
    @Transient
    private List<String> indentIdList;
    
    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }
    
    public String getBatchStatusDisp() {
        return batchStatusDisp;
    }

    public void setBatchStatusDisp(String batchStatusDisp) {
        this.batchStatusDisp = batchStatusDisp;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getBatchDate() {
        return batchDate;
    }

    public void setBatchDate(Date batchDate) {
        this.batchDate = batchDate;
    }

    public Integer getIndents() {
        return indents;
    }

    public void setIndents(Integer indents) {
        this.indents = indents;
    }

    public Integer getManagers() {
        return managers;
    }

    public void setManagers(Integer managers) {
        this.managers = managers;
    }

    public Integer getWorkers() {
        return workers;
    }

    public void setWorkers(Integer workers) {
        this.workers = workers;
    }

    public BigDecimal getIndentAmt() {
        return indentAmt;
    }

    public void setIndentAmt(BigDecimal indentAmt) {
        this.indentAmt = indentAmt;
    }

    public BigDecimal getWorkerAmt() {
        return workerAmt;
    }

    public void setWorkerAmt(BigDecimal workerAmt) {
        this.workerAmt = workerAmt;
    }

    public BigDecimal getManagerAmt() {
        return managerAmt;
    }

    public void setManagerAmt(BigDecimal managerAmt) {
        this.managerAmt = managerAmt;
    }

    public BigDecimal getProfitAmt() {
        return profitAmt;
    }

    public void setProfitAmt(BigDecimal profitAmt) {
        this.profitAmt = profitAmt;
    }

    public List<String> getIndentIdList() {
        return indentIdList;
    }

    public void setIndentIdList(List<String> indentIdList) {
        this.indentIdList = indentIdList;
    }
}
