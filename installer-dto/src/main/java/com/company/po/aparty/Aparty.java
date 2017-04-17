package com.company.po.aparty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.company.po.base.StringIdPo;

/***
 * 甲方信息表
 * 
 * @author lihome
 *
 */
@Entity
@Table(name = "ZL_APARTY", uniqueConstraints = @UniqueConstraint(columnNames = "CODE1") )
public class Aparty extends StringIdPo {

    private static final long serialVersionUID = 1L;

    // 甲方ID
    @Column(name = "CODE1", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String code1;

    // 甲方名称
    @Column(name = "NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String name1;

    @Column(name = "INDUSTRY", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String industry;

    // 登记日期
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRT_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date crtDate;

    // 所在地区 - 省
    @Column(name = "REGION_PROV", length = 10, columnDefinition = "VARCHAR(10) DEFAULT ''")
    private String regionProv;

    // 所在地区 - 市
    @Column(name = "REGION_CITY", length = 10, columnDefinition = "VARCHAR(10) DEFAULT ''")
    private String regionCity;

    // 所在地区 - 区
    @Column(name = "REGION_DIST", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String regionDist;

    @Column(name = "REMARKS", length = 500, columnDefinition = "VARCHAR(500) DEFAULT ''")
    private String remarks;

    // 通讯地址
    @Column(name = "ADDR1", length = 500, columnDefinition = "VARCHAR(500) DEFAULT ''")
    private String addr1;

    // 累计充值
    @Column(name = "PROFIT_AMT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal profitAmt;

    // 当前余额
    @Column(name = "BALANCE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal balance;
    
    //甲方状态(EnumCodes.ENABLE_STATUS)
    @Column(name = "APARTY_STATUS", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String apartyStatus;

    @Transient
    private ApartyContacts contacts1;

    @Transient
    private ApartyContacts contacts2;

    @Transient
    private List<ApartyContacts> contacts1List;

    @Transient
    private List<ApartyContacts> contacts2List;

    @Transient
    private ApartyFinance finance;

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Date getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }

    public String getRegionProv() {
        return regionProv;
    }

    public void setRegionProv(String regionProv) {
        this.regionProv = regionProv;
    }

    public String getRegionCity() {
        return regionCity;
    }

    public void setRegionCity(String regionCity) {
        this.regionCity = regionCity;
    }

    public String getRegionDist() {
        return regionDist;
    }

    public void setRegionDist(String regionDist) {
        this.regionDist = regionDist;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public BigDecimal getProfitAmt() {
        return profitAmt;
    }

    public void setProfitAmt(BigDecimal profitAmt) {
        this.profitAmt = profitAmt;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getApartyStatus() {
        return apartyStatus;
    }

    public void setApartyStatus(String apartyStatus) {
        this.apartyStatus = apartyStatus;
    }

    public ApartyContacts getContacts1() {
        return contacts1;
    }

    public void setContacts1(ApartyContacts contacts1) {
        this.contacts1 = contacts1;
    }

    public ApartyContacts getContacts2() {
        return contacts2;
    }

    public void setContacts2(ApartyContacts contacts2) {
        this.contacts2 = contacts2;
    }

    public List<ApartyContacts> getContacts1List() {
        return contacts1List;
    }

    public void setContacts1List(List<ApartyContacts> contacts1List) {
        this.contacts1List = contacts1List;
    }

    public List<ApartyContacts> getContacts2List() {
        return contacts2List;
    }

    public void setContacts2List(List<ApartyContacts> contacts2List) {
        this.contacts2List = contacts2List;
    }

    public ApartyFinance getFinance() {
        return finance;
    }

    public void setFinance(ApartyFinance finance) {
        this.finance = finance;
    }
}