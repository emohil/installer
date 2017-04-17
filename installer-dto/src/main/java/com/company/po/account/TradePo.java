package com.company.po.account;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.company.api.fw.EnumCodes;
import com.company.po.bankcard.BankCard;
import com.company.api.fw.TransformField;
import com.company.po.base.AmtPo;

@MappedSuperclass
public class TradePo extends AmtPo {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "CODE1", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String code1;

    // 结算批次ID
    @Column(name = "AP_BATCH_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String apBatchId;

    // 行号
    @Column(name = "LINE_NO", columnDefinition = "INT DEFAULT 0")
    private Integer lineNo;

    // 交易日期 - 只保留日期部分
    @Temporal(TemporalType.DATE)
    @Column(name = "TRADE_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date tradeDate;

    // 订单ID
    @Column(name = "INDENT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String indentId;

    // 交易类型
    @Column(name = "TRADE_TYPE", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String tradeType;
    
    // 交易状态
    @TransformField(groupCode = EnumCodes.TRADE_STATUS)
    @Column(name = "TRADE_STATUS", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String tradeStatus;
    
    @Transient
    private String tradeStatusDisp;
    
    // 描述
    @Column(name = "DESC1", length = 255, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String desc1;
    
    //银行卡
    @Transient
    private BankCard bankCard;
    
    @Transient
    private String cardNum;

    public TradePo() {
        this.code1 = DEF_STRING;
        this.apBatchId = DEF_STRING;
        this.lineNo = DEF_INTEGER;
        this.tradeDate = DEF_DATE;
        this.indentId = DEF_STRING;
        this.tradeType = DEF_STRING;
        this.tradeStatus = DEF_STRING;
        this.desc1 = DEF_STRING;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

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

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getIndentId() {
        return indentId;
    }

    public void setIndentId(String indentId) {
        this.indentId = indentId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getTradeStatusDisp() {
        return tradeStatusDisp;
    }

    public void setTradeStatusDisp(String tradeStatusDisp) {
        this.tradeStatusDisp = tradeStatusDisp;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public BankCard getBankCard() {
        return bankCard;
    }

    public void setBankCard(BankCard bankCard) {
        this.bankCard = bankCard;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

}
