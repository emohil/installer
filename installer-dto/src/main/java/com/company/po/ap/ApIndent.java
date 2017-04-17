package com.company.po.ap;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.company.po.base.StringIdPo;

/**
 * AP 结算批次 - 订单维度表
 * 
 * @author lihome
 *
 */
@Entity
@Table(name = "ZL_AP_INDENT")
public class ApIndent extends StringIdPo {

    private static final long serialVersionUID = 1L;

    // 结算批次ID
    @Column(name = "AP_BATCH_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String apBatchId;

    // 行号
    @Column(name = "LINE_NO", columnDefinition = "INT DEFAULT 0")
    private Integer lineNo;

    // 订单ID
    @Column(name = "INDENT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String indentId;
    
    // 订单Code
    @Column(name = "INDENT_CODE", length = 32, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String indentCode;

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

    // 订单金额
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

    public String getIndentId() {
        return indentId;
    }

    public void setIndentId(String indentId) {
        this.indentId = indentId;
    }

    public String getIndentCode() {
        return indentCode;
    }

    public void setIndentCode(String indentCode) {
        this.indentCode = indentCode;
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
}