package com.company.dto.wk;

import java.util.Date;

import javax.persistence.Transient;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.TransformField;


public class WkData {
    
    private String name1;
    
    private String mobile;
    
    private String desc1;
    
    private Date applyDate;
    
    @TransformField(groupCode = EnumCodes.SLAVE_STATUS)
    private String status;
    
    @Transient
    private String statusDisp;

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

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDisp() {
        return statusDisp;
    }

    public void setStatusDisp(String statusDisp) {
        this.statusDisp = statusDisp;
    }
    

}