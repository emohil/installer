package com.company.po.district;




import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.company.po.base.StringIdPo;

@Entity
@Table(name = "ZL_PROV")
public class Prov extends StringIdPo {

    /**
     * 开通省份表
     */
    private static final long serialVersionUID = 1L;
    
    //省份编码
    @Column(name = "CODE1", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String code1;
    
    //状态
    @Column(name = "STATUS", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String status;
    
    @JsonProperty//省份
    @Column(name = "NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String name1;
    
    //操作人ID
    @Column(name = "ADMIN_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String adminId;
    
    //操作时间
    @Column(name = "OPERATE_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date operateDate;

    @JsonProperty
    @Transient
    private List<City> cityList;
    
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }
    
}