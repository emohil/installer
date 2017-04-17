package com.company.po.indent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.company.po.base.StringIdPo;

/***
 * 订单联系人表
 * 
 * @author liurengjie
 *
 */
@Entity
@Table(name = "ZL_INDENT_CONTACT")
public class IndentContact extends StringIdPo {

    private static final long serialVersionUID = 1L;
    
    // 订单ID
    @Column(name = "INDENT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String indentId;

    // 联系人名称
    @Column(name = "NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String name1;

    // 联系人电话
    @Column(name = "MOBILE", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String mobile;

    // 监理人名称
    @Column(name = "SUP_NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String supName1;

    // 监理人电话
    @Column(name = "SUP_MOBILE", length = 100, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String supMobile;

    // 特殊需求
    @Column(name = "DEMAND", length = 200, columnDefinition = "VARCHAR(200) DEFAULT ''")
    private String demand;

    // 所在地区 - 省
    @Column(name = "REGION_PROV", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String regionProv;

    // 所在地区 - 市
    @Column(name = "REGION_CITY", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String regionCity;

    // 所在地区 - 区
    @Column(name = "REGION_DIST", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String regionDist;

    // 详细地址
    @Column(name = "DETAIL_ADDR1", length = 200, columnDefinition = "VARCHAR(200) DEFAULT ''")
    private String detailAddr1;

    public String getIndentId() {
        return indentId;
    }

    public void setIndentId(String indentId) {
        this.indentId = indentId;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSupName1() {
        return supName1;
    }

    public void setSupName1(String supName1) {
        this.supName1 = supName1;
    }

    public String getSupMobile() {
        return supMobile;
    }

    public void setSupMobile(String supMobile) {
        this.supMobile = supMobile;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
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

    public String getDetailAddr1() {
        return detailAddr1;
    }

    public void setDetailAddr1(String detailAddr1) {
        this.detailAddr1 = detailAddr1;
    }

}