package com.company.po.aparty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.company.po.base.StringIdPo;

/***
 * 甲方财务信息表
 * 
 * @author lihome
 *
 */
@Entity
@Table(name = "ZL_APARTY_FINANCE")
public class ApartyFinance extends StringIdPo {

    private static final long serialVersionUID = 1L;

    // 甲方ID
    @Column(name = "APARTY_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String apartyId;

    // 发票抬头
    @Column(name = "INVOICE_TOP", length = 200, columnDefinition = "VARCHAR(200) DEFAULT ''")
    private String invoiceTop;

    // 甲方账期
    @Column(name = "REVOLVE_TIME", length = 50, columnDefinition = "VARCHAR(50) DEFAULT ''")
    private String revolveTime;

    // 开户行
    @Column(name = "OPEN_BANK", length = 100, columnDefinition = "VARCHAR(100) DEFAULT ''")
    private String openBank;

    // 账号
    @Column(name = "CARD_NO", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String cardNo;

    // 备注
    @Column(name = "REMARKS", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String remarks;

    public String getApartyId() {
        return apartyId;
    }

    public void setApartyId(String apartyId) {
        this.apartyId = apartyId;
    }

    public String getInvoiceTop() {
        return invoiceTop;
    }

    public void setInvoiceTop(String invoiceTop) {
        this.invoiceTop = invoiceTop;
    }

    public String getRevolveTime() {
        return revolveTime;
    }

    public void setRevolveTime(String revolveTime) {
        this.revolveTime = revolveTime;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}