package com.company.po.indent;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.company.po.base.StringIdPo;

/***
 * 订单异常详情表
 * 
 * @author liurengjie
 *
 */
@Entity
@Table(name = "ZL_INDENT_EXCEP_DETAIL")
public class IndentExceptionDetail extends StringIdPo {

    private static final long serialVersionUID = 1L;

    // 异常ID
    @JsonProperty
    @Column(name = "EXCEPTION_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String exceptionId;

    // 操作日期
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OPERATE_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date operateDate;

    // 客服ID
    @Column(name = "ADMIN_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String adminId;

    // 客服名称
    @Column(name = "ADMIN_NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String adminName1;

    // 处理结果
    @Column(name = "RESULT", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String result;
    
    // 处理意见
    @Column(name = "COMMENT", length = 255, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String comment;
    
    // 责任方
    //@TransformField(groupCode = DictCodes.PART)
    @Column(name = "PART", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String part;


    public String getExceptionId() {
        return exceptionId;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getAdminName1() {
        return adminName1;
    }

    public String getResult() {
        return result;
    }

    public void setExceptionId(String exceptionId) {
        this.exceptionId = exceptionId;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public void setAdminName1(String adminName1) {
        this.adminName1 = adminName1;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }
    
}
