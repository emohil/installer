package com.company.po.ap;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.company.po.base.StringIdPo;

/**
 * AP 结算批次 - 经理人维度表
 * 
 * @author lihome
 *
 */
@Entity
@Table(name = "ZL_AP_MANAGER")
public class ApManager extends StringIdPo {

    private static final long serialVersionUID = 1L;

    // 结算批次ID
    @Column(name = "AP_BATCH_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String apBatchId;

    // 行号
    @Column(name = "LINE_NO", columnDefinition = "INT DEFAULT 0")
    private Integer lineNo;

    // 经理人ID
    @Column(name = "MANAGER_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String managerId;

    @Transient
    private String managerIdDisp;

    // 工人数
    @Column(name = "WORKERS", columnDefinition = "INT DEFAULT 0")
    private Integer workers;

    // 订单数
    @Column(name = "INDENTS", columnDefinition = "INT DEFAULT 0")
    private Integer indents;

    // 经理人收入金额
    @Column(name = "MANAGER_AMT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal managerAmt;

    public String getApBatchId() {
        return apBatchId;
    }

    public void setApBatchId(String apBatchId) {
        this.apBatchId = apBatchId;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerIdDisp() {
        return managerIdDisp;
    }

    public void setManagerIdDisp(String managerIdDisp) {
        this.managerIdDisp = managerIdDisp;
    }

    public Integer getWorkers() {
        return workers;
    }

    public void setWorkers(Integer workers) {
        this.workers = workers;
    }

    public Integer getIndents() {
        return indents;
    }

    public void setIndents(Integer indents) {
        this.indents = indents;
    }

    public BigDecimal getManagerAmt() {
        return managerAmt;
    }

    public void setManagerAmt(BigDecimal managerAmt) {
        this.managerAmt = managerAmt;
    }
}