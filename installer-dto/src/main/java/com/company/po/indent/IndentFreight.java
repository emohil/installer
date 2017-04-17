package com.company.po.indent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.company.api.fw.DictCodes;
import com.company.api.fw.TransformField;
import com.company.po.base.StringIdPo;

/***
 * 货运单表
 * 
 * @author liurengjie
 *
 */
@Entity
@Table(name = "ZL_INDENT_FREIGHT", uniqueConstraints = @UniqueConstraint(columnNames = "CODE1"))
public class IndentFreight extends StringIdPo {

    private static final long serialVersionUID = 1L;

    // 货运单号 code
    @Column(name = "CODE1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String code1;
    
    // 订单ID
    @Column(name = "INDENT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String indentId;

    // 件数
    @Column(name = "PACKAGE_NUM", length = 11, columnDefinition = "INT DEFAULT 0")
    private Integer packageNum;

    // 毛重
    @Column(name = "KGS", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String kgs;

    // 要求车型
    @TransformField(groupCode = DictCodes.VEHICLE)
    @Column(name = "CAR_MODEL", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String carModel;
    
    @Transient
    private String carModelDisp;
    
    // 车牌号
    @Column(name = "CAR_NUMBER", length = 10, columnDefinition = "VARCHAR(10) DEFAULT ''")
    private String carNumber;

    // 提货联系人
    @Column(name = "CONTACTS", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String contacts;

    // 提货联系电话
    @Column(name = "MOBILE", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String mobile;

    // 提货备注
    @Column(name = "COMMENT", length = 100, columnDefinition = "VARCHAR(100) DEFAULT ''")
    private String comment;

    // 提货详细地址
    @Column(name = "ADDR1", length = 100, columnDefinition = "VARCHAR(100) DEFAULT ''")
    private String addr1;

    // 所在地区 - 省
    @Column(name = "REGION_PROV", length = 10, columnDefinition = "VARCHAR(10) DEFAULT ''")
    private String regionProv;

    // 所在地区 - 市
    @Column(name = "REGION_CITY", length = 10, columnDefinition = "VARCHAR(10) DEFAULT ''")
    private String regionCity;

    // 所在地区 - 区
    @Column(name = "REGION_DIST", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String regionDist;

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getIndentId() {
        return indentId;
    }

    public void setIndentId(String indentId) {
        this.indentId = indentId;
    }

    public Integer getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(Integer packageNum) {
        this.packageNum = packageNum;
    }

    public String getKgs() {
        return kgs;
    }

    public void setKgs(String kgs) {
        this.kgs = kgs;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCarModelDisp() {
        return carModelDisp;
    }

    public void setCarModelDisp(String carModelDisp) {
        this.carModelDisp = carModelDisp;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
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
    
}