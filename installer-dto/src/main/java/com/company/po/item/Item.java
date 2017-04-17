package com.company.po.item;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.company.api.fw.DictCodes;
import com.company.po.aparty.Aparty;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.company.api.fw.TransformField;
import com.company.po.base.StringIdPo;



@Entity
@Table(name = "ZL_ITEM")
public class Item extends StringIdPo {

    /**
     * 项目基本信息
     */
    private static final long serialVersionUID = 1L;

    @JsonProperty//项目编号
    @Column(name = "CODE1", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", unique = true, updatable = false)
    private String code1;

    @JsonProperty//项目名称
    @Column(name = "NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String name1;

    @Column(name = "ITEM_MONEY", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal itemMoney;//项目金额

    @Column(name = "INDENT_AMT", length = 4, columnDefinition = "INT DEFAULT 0")
    private Integer indentAmt;//预计订单量
    
    @Transient//实际下单量
    private Integer actualAmt;
    
    @Transient//接单量
    private Integer receiveAmt;
    
    @JsonProperty//结算类型（现付/后付）
    @Column(name = "PAY_TYPE", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String payType;

    @Temporal(TemporalType.TIMESTAMP)//项目开始时间
    @Column(name = "BEGIN_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date beginDate;

    @Temporal(TemporalType.TIMESTAMP)//项目结束时间
    @Column(name = "OVER_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date overDate;

    @JsonProperty//项目所属甲方
    @Column(name = "APARTY_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String apartyId;

    @Transient
    private String apartyIdDisp;

    @JsonProperty//合同号
    @Column(name = "PACT_NUM", length = 40, columnDefinition = "VARCHAR(40) DEFAULT ''")
    private String pactNum;

    @JsonProperty//项目要求
    @Column(name = "INDENT_REQUIRE", length = 500, columnDefinition = "VARCHAR(500) DEFAULT ''")
    private String indentRequire;

    @JsonProperty//项目竞标状态（未发布、竞标中、竞标结束）
    @Column(name = "COMPETE_STATUS", length = 20, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String competeStatus;

    @JsonProperty//项目执行状态（未执行、执行中、执行完毕）
    @Column(name = "EXECUTE_STATUS", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String executeStatus;

    //服务区域(省)
    @Column(name = "AREA_PROVINCE", length = 10, columnDefinition = "VARCHAR(10) DEFAULT ''")
    private String areaProvince;

    //服务区域(市)
    @Column(name = "AREA_CITY", length = 10, columnDefinition = "VARCHAR(10) DEFAULT ''")
    private String areaCity;

    // 所在地区 - 区
    @Column(name = "AREA_DIST", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String areaDist;

    @TransformField(groupCode = DictCodes.ITEM_STATUS)
    @JsonProperty//项目状态（正常、停用）
    @Column(name = "STATUS", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String status;

    @Transient
    private String statusDisp;

    @JsonProperty//项目结算状态（未结算、已结算）
    @Column(name = "PAY_STATUS", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String payStatus;

    @JsonProperty//备注
    @Column(name = "REMARKS", length =255, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String remarks;

    @Transient
    private ItemContacts contacts1;

    @Transient
    private ItemContacts contacts2;

    @Transient
    private List<ItemContacts> contacts1List;

    @Transient
    private List<ItemContacts> contacts2List;

    @Transient
    private Aparty aparty;

    @Transient
    private List<ItemPrice> itemPriceList;

    @Transient
    private String serveType;

    public String getAreaDist() {
        return areaDist;
    }

    public void setAreaDist(String areaDist) {
        this.areaDist = areaDist;
    }

    public String getServeType() {
        return serveType;
    }

    public void setServeType(String serveType) {
        this.serveType = serveType;
    }

    public List<ItemPrice> getItemPriceList() {
        return itemPriceList;
    }

    public void setItemPriceList(List<ItemPrice> itemPriceList) {
        this.itemPriceList = itemPriceList;
    }

    public Aparty getAparty() {
        return aparty;
    }

    public void setAparty(Aparty aparty) {
        this.aparty = aparty;
    }

    public String getStatusDisp() {
        return statusDisp;
    }

    public void setStatusDisp(String statusDisp) {
        this.statusDisp = statusDisp;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public ItemContacts getContacts1() {
        return contacts1;
    }

    public void setContacts1(ItemContacts contacts1) {
        this.contacts1 = contacts1;
    }

    public ItemContacts getContacts2() {
        return contacts2;
    }

    public void setContacts2(ItemContacts contacts2) {
        this.contacts2 = contacts2;
    }

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

    public BigDecimal getItemMoney() {
        return itemMoney;
    }

    public void setItemMoney(BigDecimal itemMoney) {
        this.itemMoney = itemMoney;
    }

    public Integer getIndentAmt() {
        return indentAmt;
    }

    public void setIndentAmt(Integer indentAmt) {
        this.indentAmt = indentAmt;
    }
    
    public Integer getActualAmt() {
        return actualAmt;
    }

    public void setActualAmt(Integer actualAmt) {
        this.actualAmt = actualAmt;
    }

    public Integer getReceiveAmt() {
        return receiveAmt;
    }

    public void setReceiveAmt(Integer receiveAmt) {
        this.receiveAmt = receiveAmt;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Date getOverDate() {
        return overDate;
    }

    public void setOverDate(Date overDate) {
        this.overDate = overDate;
    }

    public String getApartyId() {
        return apartyId;
    }

    public void setApartyId(String apartyId) {
        this.apartyId = apartyId;
    }

    public String getApartyIdDisp() {
        return apartyIdDisp;
    }

    public void setApartyIdDisp(String apartyIdDisp) {
        this.apartyIdDisp = apartyIdDisp;
    }

    public String getPactNum() {
        return pactNum;
    }

    public void setPactNum(String pactNum) {
        this.pactNum = pactNum;
    }

    public String getIndentRequire() {
        return indentRequire;
    }

    public void setIndentRequire(String indentRequire) {
        this.indentRequire = indentRequire;
    }

    public String getCompeteStatus() {
        return competeStatus;
    }

    public void setCompeteStatus(String competeStatus) {
        this.competeStatus = competeStatus;
    }

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getAreaProvince() {
        return areaProvince;
    }

    public void setAreaProvince(String areaProvince) {
        this.areaProvince = areaProvince;
    }

    public String getAreaCity() {
        return areaCity;
    }

    public void setAreaCity(String areaCity) {
        this.areaCity = areaCity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<ItemContacts> getContacts1List() {
        return contacts1List;
    }

    public void setContacts1List(List<ItemContacts> contacts1List) {
        this.contacts1List = contacts1List;
    }

    public List<ItemContacts> getContacts2List() {
        return contacts2List;
    }

    public void setContacts2List(List<ItemContacts> contacts2List) {
        this.contacts2List = contacts2List;
    }

}