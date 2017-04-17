package com.company.po.base;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class AmtPo extends StringIdPo {

    private static final long serialVersionUID = 1L;

    // 期初金额 - Previous
    @Column(name = "PRE_AMT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal preAmt;

    // 当次金额 - Current
    @Column(name = "CUR_AMT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal curAmt;

    // 期末余额 - Balance
    @Column(name = "BAL_AMT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal balAmt;
    
    //手续费
    @Transient
    private BigDecimal factorage;
    
    //实际转账金额
    @Transient
    private BigDecimal  actualAmt;

    public BigDecimal getPreAmt() {
        return preAmt;
    }

    public void setPreAmt(BigDecimal preAmt) {
        this.preAmt = preAmt;
    }

    public BigDecimal getCurAmt() {
        return curAmt;
    }

    public void setCurAmt(BigDecimal curAmt) {
        this.curAmt = curAmt;
    }

    public BigDecimal getBalAmt() {
        return balAmt;
    }

    public void setBalAmt(BigDecimal balAmt) {
        this.balAmt = balAmt;
    }

    public BigDecimal getFactorage() {
        return factorage;
    }

    public void setFactorage(BigDecimal factorage) {
        this.factorage = factorage;
    }

    public BigDecimal getActualAmt() {
        return actualAmt;
    }

    public void setActualAmt(BigDecimal actualAmt) {
        this.actualAmt = actualAmt;
    }
    
}