package com.company.po.ap;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.company.po.base.StringIdPo;

/**
 * AP 结算批次 - 工人维度表
 * 
 * @author lihome
 *
 */
@Entity
@Table(name = "ZL_AP_WORKER")
public class ApWorker extends StringIdPo {

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

    // 工人ID
    @Column(name = "WORKER_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String workerId;

    @Transient
    private String workerIdDisp;

    // 订单数
    @Column(name = "INDENTS", columnDefinition = "INT DEFAULT 0")
    private Integer indents;

    // 工人收入金额
    @Column(name = "WORKER_AMT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal workerAmt;

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

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getWorkerIdDisp() {
        return workerIdDisp;
    }

    public void setWorkerIdDisp(String workerIdDisp) {
        this.workerIdDisp = workerIdDisp;
    }

    public Integer getIndents() {
        return indents;
    }

    public void setIndents(Integer indents) {
        this.indents = indents;
    }

    public BigDecimal getWorkerAmt() {
        return workerAmt;
    }

    public void setWorkerAmt(BigDecimal workerAmt) {
        this.workerAmt = workerAmt;
    }
}
