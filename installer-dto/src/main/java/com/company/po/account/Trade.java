package com.company.po.account;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 账号交易流水表 基础类
 * 
 * @author lihome
 *
 */
@MappedSuperclass
public class Trade extends TradePo {

    private static final long serialVersionUID = 1L;

    // 保证金
    @Column(name = "DEPOSIT_AMT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal depositAmt;

    public BigDecimal getDepositAmt() {
        return depositAmt;
    }

    public void setDepositAmt(BigDecimal depositAmt) {
        this.depositAmt = depositAmt;
    }
    
    public Trade() {
        this.depositAmt = DEF_DECIMAL;
    }
}
