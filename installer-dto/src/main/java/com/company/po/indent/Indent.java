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
import javax.persistence.UniqueConstraint;

import com.company.api.fw.EnumCodes;
import com.company.po.aparty.Aparty;
import com.company.po.fs.FileIndex;
import com.company.po.item.Item;
import com.company.po.sctype.SctypeSort;
import com.company.api.fw.TransformField;
import com.company.po.base.StringIdPo;
import com.company.util.Dto;

/***
 * 订单信息表
 * 
 * @author liurengjie
 *
 */

@Entity
@Table(name = "ZL_INDENT", uniqueConstraints = @UniqueConstraint(columnNames = "CODE1") )
public class Indent extends StringIdPo {

    private static final long serialVersionUID = 1L;

    // 订单CODE1
    @Column(name = "CODE1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''", updatable = false)
    private String code1;
    
    // 原订单CODE1
    @Column(name = "ORIGINAL_CODE1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String originalCode1;

    @Transient
    private String newCode1;

    // 来源 ( 后改为 上门次数 ) 
    @TransformField(groupCode = EnumCodes.INDENT_VISIT_SOURCE)
    @Column(name = "SOURCE", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String source;

    @Transient
    private String sourceDisp;

    // 订单的来源（正单，免费单，售后单）
    @TransformField(groupCode = EnumCodes.INDENT_SOURCE)
    @Column(name = "INDENT_SOURCE", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String indentSource;
    
    @Transient
    private String indentSourceDisp;

    // 是否为指派单
    //@TransformField(groupCode = EnumCodes.YESNO)
    @Column(name = "INDENT_ASSIGN", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String indentAssign;
    
    @Transient
    private Boolean indentAssignSelected;
    
    // 所属甲方ID
    @Column(name = "APARTY_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String apartyId;

    // 所属项目ID
    @Column(name = "ITEM_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String itemId;

    // 接单工人 ID
    @Column(name = "WORKER_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String workerId;
    
    // 接单工人名称
    @Transient
    private String workerName1;
    
    // 接单工人电话
    @Transient
    private String workerMobile;

    // 经理人 ID
    @Column(name = "MANAGER_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String managerId;
    
    // 经理人名称
    @Transient
    private String managerName1;

    // 总报价
    @Column(name = "SUM", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal sum;

    // 实际支付价
    @Column(name = "ACTUAL_PAY", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal actualPay;

    // 工人服务费
    @Column(name = "WORKER_FEE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal workerFee;

    // 工人订单奖励
    @Column(name = "WORKER_AWARD", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal workerAward;

    // 经理人佣金
    @Column(name = "MANAGER_FEE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal managerFee;

    // 经理人奖励
    @Column(name = "MANAGER_AWARD", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal managerAward;

    // 利润
    @Column(name = "PROFIT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal profit;

    @Transient
    private List<FileIndex> dwgImgList;

    // 订单发布状态：
    //@TransformField(groupCode = EnumCodes.INDENT_RELEASE_STATUS)
    @Column(name = "RELEASE_STATUS", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String releaseStatus;

    @Transient
    private String releaseStatusDisp;

    // 订单发布时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RELEASE_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date releaseDate;

    // 订单执行状态：
    @TransformField(groupCode = EnumCodes.INDENT_EXECUTE_STATUS)
    @Column(name = "EXECUTE_STATUS", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String executeStatus;

    @Transient
    private String executeStatusDisp;

    // 订单的综合api状态
    @Transient
    private String indentShowStatus;

    // 订单的综合api状态描述
    @Transient
    private String indentShowStatusDisp;

    // 订单状态
    @TransformField(groupCode = EnumCodes.INDENT_STATUS)
    @Column(name = "STATUS", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String status;

    @Transient
    private String statusDisp;

    // 评价状态
    @TransformField(groupCode = EnumCodes.EVALUATE_STATUS)
    @Column(name = "EVALUATE_STATUS", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String evaluateStatus;

    @Transient
    private String evaluateStatusDisp;

    // 节点状态
    @Column(name = "PROG_STATUS", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String progStatus;
    
    // 评分
    @Column(name = "EVALUATE_SCORES", precision = 11, scale = 3, columnDefinition = "DOUBLE(11, 3) DEFAULT 0")
    private double evaluateScores;

    // 订单异常状态分类
    @TransformField(groupCode = EnumCodes.INDENT_EXCEP_STATUS)
    @Column(name = "EXCEP_STATUS", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String excepStatus;

    @Transient
    private String excepStatusDisp;

    // 登记日期
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRT_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date crtDate;

    // 服务时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SERVICE_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date serviceDate;

    // 订单服务时长
    @Column(name = "DURATION_TIME", length = 11, columnDefinition = "INT DEFAULT 0")
    private Integer durationTime;

    // 完工时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FINISH_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date finishDate;
    
    // 评价时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EVALUATE_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date evaluateDate;

    // 预计完工时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PLAN_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date planDate;

    // 所在地区 - 省
    @Column(name = "REGION_PROV", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String regionProv;

    // 所在地区 - 市
    @Column(name = "REGION_CITY", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String regionCity;

    // 所在地区 - 区
    @Column(name = "REGION_DIST", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String regionDist;

    // 服务类型
    @Column(name = "SERVE_TYPE", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String serveType;

    @Transient
    private String serveTypeDisp;

    // 服务类别个数
    @Column(name = "SORT_COUNT", columnDefinition = "INT DEFAULT 0")
    private Integer sortCount;
    
    // 订单备注
    @Column(name = "COMMENT", length = 500, columnDefinition = "VARCHAR(500) DEFAULT ''")
    private String comment;
    
    //付款结算
    //@TransformField(groupCode = EnumCodes.YESNO)
    @Column(name = "AP_SETTLEMENT", columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String apSettlement;
    
    //收款结算
    //@TransformField(groupCode = EnumCodes.YESNO)
    @Column(name = "AR_SETTLEMENT", columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String arSettlement;
    
    // 录入人员Id
    @Column(name = "ADMIN_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String adminId;

    // 录入人员名称
    @Column(name = "ADMIN_NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String adminName1;

    @Transient
    private SctypeSort sctypeSort;

    //订单联系人
    @Transient
    private IndentContact contact;

    @Transient
    private Item item;

    @Transient
    private Aparty aparty;

    //货运单
    @Transient
    private IndentFreight indentFreight;

    //订单内容报价
    @Transient
    private IndentContent indentContent;

    @Transient
    private Dto indentPriceDto;

    @Transient
    private List<IndentContent> indentPriceList;

    @Transient
    private List<SctypeSort> sctypeSortList;
    
    @Transient
    private Dto indentSortDto;
    
    public Indent () {
        this.indentSource = DEF_STRING;
        this.sum = DEF_DECIMAL;
        this.actualPay = DEF_DECIMAL;
        this.profit = DEF_DECIMAL;
        this.apartyId = DEF_STRING;
        this.apSettlement = DEF_STRING;
        this.arSettlement = DEF_STRING;
        this.managerId = DEF_STRING;
        this.managerAward = DEF_DECIMAL;
        this.managerFee = DEF_DECIMAL;
        this.workerId = DEF_STRING;
        this.workerFee = DEF_DECIMAL;
        this.workerAward = DEF_DECIMAL;
        this.progStatus = DEF_STRING;
        this.sortCount = DEF_INTEGER;
        this.comment = DEF_STRING;
        this.releaseStatus = DEF_STRING;
        this.originalCode1 = DEF_STRING;
        this.indentAssign = DEF_STRING;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getOriginalCode1() {
        return originalCode1;
    }

    public void setOriginalCode1(String originalCode1) {
        this.originalCode1 = originalCode1;
    }

    public String getNewCode1() {
        return newCode1;
    }

    public void setNewCode1(String newCode1) {
        this.newCode1 = newCode1;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceDisp() {
        return sourceDisp;
    }

    public void setSourceDisp(String sourceDisp) {
        this.sourceDisp = sourceDisp;
    }

    public String getIndentSource() {
        return indentSource;
    }

    public void setIndentSource(String indentSource) {
        this.indentSource = indentSource;
    }

    public String getIndentSourceDisp() {
        return indentSourceDisp;
    }

    public void setIndentSourceDisp(String indentSourceDisp) {
        this.indentSourceDisp = indentSourceDisp;
    }

    public String getIndentAssign() {
        return indentAssign;
    }

    public void setIndentAssign(String indentAssign) {
        this.indentAssign = indentAssign;
    }

    public Boolean getIndentAssignSelected() {
        return indentAssignSelected;
    }

    public void setIndentAssignSelected(Boolean indentAssignSelected) {
        this.indentAssignSelected = indentAssignSelected;
    }

    public String getApartyId() {
        return apartyId;
    }

    public void setApartyId(String apartyId) {
        this.apartyId = apartyId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getWorkerName1() {
        return workerName1;
    }

    public void setWorkerName1(String workerName1) {
        this.workerName1 = workerName1;
    }

    public String getWorkerMobile() {
        return workerMobile;
    }

    public void setWorkerMobile(String workerMobile) {
        this.workerMobile = workerMobile;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerName1() {
        return managerName1;
    }

    public void setManagerName1(String managerName1) {
        this.managerName1 = managerName1;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getActualPay() {
        return actualPay;
    }

    public void setActualPay(BigDecimal actualPay) {
        this.actualPay = actualPay;
    }

    public BigDecimal getWorkerFee() {
        return workerFee;
    }

    public void setWorkerFee(BigDecimal workerFee) {
        this.workerFee = workerFee;
    }

    public BigDecimal getWorkerAward() {
        return workerAward;
    }

    public void setWorkerAward(BigDecimal workerAward) {
        this.workerAward = workerAward;
    }

    public BigDecimal getManagerFee() {
        return managerFee;
    }

    public void setManagerFee(BigDecimal managerFee) {
        this.managerFee = managerFee;
    }

    public BigDecimal getManagerAward() {
        return managerAward;
    }

    public void setManagerAward(BigDecimal managerAward) {
        this.managerAward = managerAward;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public List<FileIndex> getDwgImgList() {
        return dwgImgList;
    }

    public void setDwgImgList(List<FileIndex> dwgImgList) {
        this.dwgImgList = dwgImgList;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public String getReleaseStatusDisp() {
        return releaseStatusDisp;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public void setReleaseStatusDisp(String releaseStatusDisp) {
        this.releaseStatusDisp = releaseStatusDisp;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getExecuteStatusDisp() {
        return executeStatusDisp;
    }

    public void setExecuteStatusDisp(String executeStatusDisp) {
        this.executeStatusDisp = executeStatusDisp;
    }

    public String getIndentShowStatus() {
        return indentShowStatus;
    }

    public void setIndentShowStatus(String indentShowStatus) {
        this.indentShowStatus = indentShowStatus;
    }

    public String getIndentShowStatusDisp() {
        return indentShowStatusDisp;
    }

    public void setIndentShowStatusDisp(String indentShowStatusDisp) {
        this.indentShowStatusDisp = indentShowStatusDisp;
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

    public String getEvaluateStatus() {
        return evaluateStatus;
    }

    public void setEvaluateStatus(String evaluateStatus) {
        this.evaluateStatus = evaluateStatus;
    }

    public String getEvaluateStatusDisp() {
        return evaluateStatusDisp;
    }

    public void setEvaluateStatusDisp(String evaluateStatusDisp) {
        this.evaluateStatusDisp = evaluateStatusDisp;
    }

    public String getProgStatus() {
        return progStatus;
    }

    public void setProgStatus(String progStatus) {
        this.progStatus = progStatus;
    }
    
    public double getEvaluateScores() {
        return evaluateScores;
    }

    public void setEvaluateScores(double evaluateScores) {
        this.evaluateScores = evaluateScores;
    }

    public String getExcepStatus() {
        return excepStatus;
    }

    public void setExcepStatus(String excepStatus) {
        this.excepStatus = excepStatus;
    }

    public String getExcepStatusDisp() {
        return excepStatusDisp;
    }

    public void setExcepStatusDisp(String excepStatusDisp) {
        this.excepStatusDisp = excepStatusDisp;
    }

    public Date getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public Integer getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Integer durationTime) {
        this.durationTime = durationTime;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getEvaluateDate() {
        return evaluateDate;
    }

    public void setEvaluateDate(Date evaluateDate) {
        this.evaluateDate = evaluateDate;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }


    public String getServeType() {
        return serveType;
    }

    public void setServeType(String serveType) {
        this.serveType = serveType;
    }

    public String getServeTypeDisp() {
        return serveTypeDisp;
    }

    public void setServeTypeDisp(String serveTypeDisp) {
        this.serveTypeDisp = serveTypeDisp;
    }

    public Integer getSortCount() {
        return sortCount;
    }

    public void setSortCount(Integer sortCount) {
        this.sortCount = sortCount;
    }

    public String getRegionProv() {
        return regionProv;
    }

    public String getRegionCity() {
        return regionCity;
    }

    public String getRegionDist() {
        return regionDist;
    }

    public void setRegionProv(String regionProv) {
        this.regionProv = regionProv;
    }

    public void setRegionCity(String regionCity) {
        this.regionCity = regionCity;
    }

    public void setRegionDist(String regionDist) {
        this.regionDist = regionDist;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getApSettlement() {
        return apSettlement;
    }

    public void setApSettlement(String apSettlement) {
        this.apSettlement = apSettlement;
    }

    public String getArSettlement() {
        return arSettlement;
    }

    public void setArSettlement(String arSettlement) {
        this.arSettlement = arSettlement;
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

    public SctypeSort getSctypeSort() {
        return sctypeSort;
    }

    public void setSctypeSort(SctypeSort sctypeSort) {
        this.sctypeSort = sctypeSort;
    }

    public IndentContact getContact() {
        return contact;
    }

    public void setContact(IndentContact contact) {
        this.contact = contact;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Aparty getAparty() {
        return aparty;
    }

    public void setAparty(Aparty aparty) {
        this.aparty = aparty;
    }

    public IndentFreight getIndentFreight() {
        return indentFreight;
    }

    public void setIndentFreight(IndentFreight indentFreight) {
        this.indentFreight = indentFreight;
    }

    public IndentContent getIndentContent() {
        return indentContent;
    }

    public void setIndentContent(IndentContent indentContent) {
        this.indentContent = indentContent;
    }

    public Dto getIndentPriceDto() {
        return indentPriceDto;
    }

    public void setIndentPriceDto(Dto indentPriceDto) {
        this.indentPriceDto = indentPriceDto;
    }

    public List<IndentContent> getIndentPriceList() {
        return indentPriceList;
    }

    public void setIndentPriceList(List<IndentContent> indentPriceList) {
        this.indentPriceList = indentPriceList;
    }

    public List<SctypeSort> getSctypeSortList() {
        return sctypeSortList;
    }

    public void setSctypeSortList(List<SctypeSort> sctypeSortList) {
        this.sctypeSortList = sctypeSortList;
    }

    public Dto getIndentSortDto() {
        return indentSortDto;
    }

    public void setIndentSortDto(Dto indentSortDto) {
        this.indentSortDto = indentSortDto;
    }
}