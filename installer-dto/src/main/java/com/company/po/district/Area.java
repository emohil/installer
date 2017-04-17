package com.company.po.district;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.company.po.base.StringIdPo;

@Entity
@Table(name = "ZL_AREA")
public class Area extends StringIdPo {

    /**
     * 开通城市区表
     */
    private static final long serialVersionUID = 1L;
    
    @JsonProperty//城市ID
    @Column(name = "CITY_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String cityId;
    
    //区编码
    @Column(name = "CODE1", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String code1;
    
    @JsonProperty//区名称
    @Column(name = "NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String name1;
    
    //区加价率(%)
    @Column(name = "ADD_RATE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal addRate;
    
    //工人上限（0表示无上线）
    @Column(name = "MAX_WORKERS", length = 11, columnDefinition = "INT DEFAULT 0")
    private Integer maxWorkers;
    
    //每单额外增加服务费
    @Column(name = "EXTRA_COST", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal extraCost;
    
    //操作人ID
    @Column(name = "ADMIN_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String adminId;
    
    //操作时间
    @Column(name = "OPERATE_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date operateDate;
    
    @Transient
    private List<Area> areaList;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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

    public BigDecimal getAddRate() {
        return addRate;
    }

    public void setAddRate(BigDecimal addRate) {
        this.addRate = addRate;
    }

    public Integer getMaxWorkers() {
        return maxWorkers;
    }

    public void setMaxWorkers(Integer maxWorkers) {
        this.maxWorkers = maxWorkers;
    }

    public BigDecimal getExtraCost() {
        return extraCost;
    }

    public void setExtraCost(BigDecimal extraCost) {
        this.extraCost = extraCost;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }

}