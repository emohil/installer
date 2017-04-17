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
@Table(name = "ZL_CITY")
public class City extends StringIdPo {

    /**
     * 开通城市表
     */
    private static final long serialVersionUID = 1L;
    
    @JsonProperty//省份ID
    @Column(name = "PROV_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String provId;
    
    @JsonProperty//城市编码
    @Column(name = "CODE1", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String code1;
    
    @JsonProperty//城市名称
    @Column(name = "NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String name1;
    
    //城市加价率(%)
    @Column(name = "ADD_RATE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal addRate;
    
    //工人上限（0表示无上限）
    @Column(name = "MAX_WORKERS", length = 11, columnDefinition = "INT DEFAULT 0")
    private Integer maxWorkers;
    
    //经理人上限（0表示无上限）
    @Column(name = "MAX_MANAGERS", length = 11, columnDefinition = "INT DEFAULT 0")
    private Integer maxManagers;
    
    //所属片区
    @Column(name = "DISTRICT", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String district;
    
    //状态
    @Column(name = "STATUS", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String status;
    
    //开通服务编码
    @Column(name = "SERVICE_CODES", length = 100, columnDefinition = "VARCHAR(100) DEFAULT ''")
    private String serviceCodes;
    
    //操作人ID
    @Column(name = "ADMIN_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String adminId;
    
    //操作时间
    @Column(name = "OPERATE_DATE")
    private Date operateDate;

    @JsonProperty
    @Transient
    private List<Area> areaList;

    public String getProvId() {
        return provId;
    }

    public void setProvId(String provId) {
        this.provId = provId;
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

    public Integer getMaxManagers() {
        return maxManagers;
    }

    public void setMaxManagers(Integer maxManagers) {
        this.maxManagers = maxManagers;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceCodes() {
        return serviceCodes;
    }

    public void setServiceCodes(String serviceCodes) {
        this.serviceCodes = serviceCodes;
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