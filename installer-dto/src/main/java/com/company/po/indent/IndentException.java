package com.company.po.indent;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.company.api.fw.EnumCodes;
import com.company.po.account.Account;
import com.company.po.fs.FileIndex;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.company.api.fw.TransformField;
import com.company.po.base.StringIdPo;

/***
 * 订单异常表
 * 
 * @author liurengjie
 *
 */
@Entity
@Table(name = "ZL_INDENT_EXCEP")
public class IndentException extends StringIdPo {

    private static final long serialVersionUID = 1L;

    // 订单ID
    @JsonProperty
    @Column(name = "INDENT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String indentId;

    // 订单节点ID
    @JsonProperty
    @Column(name = "INDENT_NODE_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String indentNodeId;

    // 提报人ID
    @JsonProperty
    @Column(name = "REP_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String repId;
    
    // 提报内容
    @Column(name = "CONTENT", length = 255, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String content;
    
    // 执行状态
    @TransformField(groupCode = EnumCodes.INDENT_EXECUTE_STATUS)
    @Column(name = "EXECUTE_STATUS", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String executeStatus;
    
    @Transient
    private String executeStatusDisp;

    // 客服ID
    @Column(name = "ADMIN_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String adminId;

    // 客服名称
    @Column(name = "ADMIN_NAME1", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String adminName1;

    // 处理结果
    @TransformField(groupCode = EnumCodes.EXCEPTION_RESULT)
    @Column(name = "RESULT", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String result;

    @Transient
    private String resultDisp;

    // 责任方
    //@TransformField(groupCode = DictCodes.PART)
    @Column(name = "PART", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String part;

    // 处理意见
    @Column(name = "IDEA", length = 255, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String idea;
    
    // 扣除工匠保证金
    @Column(name = "WORKER_DEPOSIT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal workerDeposit;

    // 扣除经理人保证金
    @Column(name = "MANAGER_DEPOSIT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal managerDeposit;
    
    // 受理日期
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ACCEPTED_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date acceptedDate;

    // 处理完成日期
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FINISH_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date finishDate;

    @Transient
    private Account account;

    @Transient
    private List<FileIndex> imgUrlList;

    public IndentException() {
        this.workerDeposit = DEF_DECIMAL;
        this.managerDeposit = DEF_DECIMAL;
    }

    public Account getAccount() {
        return account;
    }

    public String getResultDisp() {
        return resultDisp;
    }

    public void setResultDisp(String resultDisp) {
        this.resultDisp = resultDisp;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getExecuteStatusDisp() {
        return executeStatusDisp;
    }

    public void setExecuteStatusDisp(String executeStatusDisp) {
        this.executeStatusDisp = executeStatusDisp;
    }

    public String getIndentId() {
        return indentId;
    }

    public void setIndentId(String indentId) {
        this.indentId = indentId;
    }

    public String getIndentNodeId() {
        return indentNodeId;
    }

    public void setIndentNodeId(String indentNodeId) {
        this.indentNodeId = indentNodeId;
    }

    public String getRepId() {
        return repId;
    }

    public void setRepId(String repId) {
        this.repId = repId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName1() {
        return adminName1;
    }

    public void setAdminName1(String adminName1) {
        this.adminName1 = adminName1;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getIdea() {
        return idea;
    }

    public void setIdea(String idea) {
        this.idea = idea;
    }

    public BigDecimal getWorkerDeposit() {
        return workerDeposit;
    }

    public BigDecimal getManagerDeposit() {
        return managerDeposit;
    }

    public void setWorkerDeposit(BigDecimal workerDeposit) {
        this.workerDeposit = workerDeposit;
    }

    public void setManagerDeposit(BigDecimal managerDeposit) {
        this.managerDeposit = managerDeposit;
    }

    public Date getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(Date acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public List<FileIndex> getImgUrlList() {
        return imgUrlList;
    }

    public void setImgUrlList(List<FileIndex> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }
}
