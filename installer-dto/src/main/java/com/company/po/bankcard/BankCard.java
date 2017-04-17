package com.company.po.bankcard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.company.po.base.StringIdPo;


@Entity
@Table(name = "ZL_BANK_CARD")
public class BankCard extends StringIdPo {

    private static final long serialVersionUID = 1L;

    //所有者ID
    @Column(name = "OWNER_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String ownerId;
    
    //银行名称
    @Column(name = "BANK_NAME", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''", updatable = false)
    private String bankName;
    
    //银行卡号
    @Column(name = "CARD_NO", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String cardNo;
    
    //户主姓名
    @Column(name = "CARD_OWNER", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String cardOwner;
    
    //银行卡状态
    @Column(name = "STATUS", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String status;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}