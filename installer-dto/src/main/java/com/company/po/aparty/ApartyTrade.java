package com.company.po.aparty;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.company.po.base.StringIdPo;


@Entity
@Table(name = "ZL_APARTY_TRADE")
public class ApartyTrade extends StringIdPo {

    private static final long serialVersionUID = 1L;

    //甲方ID
    @Column(name = "APARTY_ID", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''", updatable = false)
    private String apartyId;
    
    //累计充值
    @Column(name = "PROFIT_AMT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal profitAmt;
    
    //本次操作金额
    @Column(name = "MONEY", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal money;
    
    //操作
    @Column(name = "OPERATION", length = 2, columnDefinition = "VARCHAR(2) DEFAULT ''")
    private String operation;
    
    //当前余额
    @Column(name = "BALANCE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal balance;
    
    //备注
    @Column(name = "REMARKS", length = 255, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String remarks;
    
    //来源
    @Column(name = "SOURCE", length = 10, columnDefinition = "VARCHAR(10) DEFAULT ''")
    private String source;

	public String getApartyId() {
		return apartyId;
	}

	public void setApartyId(String apartyId) {
		this.apartyId = apartyId;
	}

	public BigDecimal getProfitAmt() {
		return profitAmt;
	}

	public void setProfitAmt(BigDecimal profitAmt) {
		this.profitAmt = profitAmt;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
    
}