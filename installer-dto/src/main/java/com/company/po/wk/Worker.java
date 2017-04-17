package com.company.po.wk;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.company.api.fw.DictCodes;
import com.company.api.fw.EnumCodes;
import com.company.po.account.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.company.api.fw.TransformField;
import com.company.po.base.StringIdPo;

@Entity
@Table(name = "ZL_WORKER", uniqueConstraints = @UniqueConstraint(columnNames = "CODE1") )
public class Worker extends StringIdPo {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // 工人工号
    @JsonProperty
    @Column(name = "CODE1", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String code1;

    // 账号ID
    @JsonProperty
    @Column(name = "ACCOUNT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String accountId;

    // 交通工具持有方式
    @TransformField(groupCode = DictCodes.VEHICLE_BELONGS)
    @Column(name = "HOLD_WAY", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String holdWay;

    @Transient
    private String holdWayDisp;

    // 交通工具
    @TransformField(groupCode = DictCodes.VEHICLE)
    @Column(name = "VEHICLE", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String vehicle;

    @Transient
    private String vehicleDisp;

    // 交通工具审核状态
    @JsonProperty
    @TransformField(groupCode = EnumCodes.CHECK_STATUS)
    @Column(name = "VEHICLE_STATUS", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String vehicleStatus;

    @Transient
    private String vehicleStatusDisp;

    // 车辆牌照
    @Column(name = "CAR_NUMBER", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String carNumber;

    // 驾驶证照片
    @Column(name = "DRIVING_IMG", length = 255, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String drivingImg;

    // 行驶证照片
    @Column(name = "VEHICLE_IMG", length = 255, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String vehicleImg;

    // 所属经理人 ID
    @Column(name = "MANAGER_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String managerId;

    // 申请经理人 意见
    @Column(name = "MANAGER_IDEA", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String managerIdea;

    // registrationIds
    @Transient
    private String registrationId;

    // 审核状态
    @JsonProperty
    @TransformField(groupCode = EnumCodes.CHECK_STATUS)
    @Column(name = "STATUS", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String status;

    // 角色状态
    @JsonProperty
    @TransformField(groupCode = EnumCodes.ACCOUNT_STATUS)
    @Column(name = "ROLE_STATUS", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String roleStatus;

    // 技能类型
    @Column(name = "SERVICE_CATEGORY", length = 255, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String serviceCategory;

    // 技能工种
    @Column(name = "SKILL_TYPE", length = 500, columnDefinition = "VARCHAR(500) DEFAULT ''")
    private String skillType;

    // 服务区域(省)
    @JsonProperty
    @Column(name = "SERVICE_PROVINCE", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String serviceProvince;

    // 服务区域(市)
    @JsonProperty
    @Column(name = "SERVICE_CITY", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String serviceCity;

    // 服务区域(区县)
    @JsonProperty
    @Column(name = "SERVICE_COUNTY", length = 1024, columnDefinition = "VARCHAR(1024) DEFAULT ''")
    private String serviceCounty;

    // 审核人
    @Column(name = "VERIFIER", length = 100, columnDefinition = "VARCHAR(100) DEFAULT ''")
    private String verifier;

    // 审核人 ID
    @Column(name = "VERIFIER_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String verifierId;
    
    // 审核时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VERIFIER_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date verifierDate;

    // 关联银行卡 ID
    @Column(name = "BANK_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String bankId;

    // 备注（用于存放审核反馈信息）
    @Column(name = "REMARKS", length = 500, columnDefinition = "VARCHAR(500) DEFAULT ''")
    private String remarks;

    // 工匠等级（初步，每次订单评价完之后计算）
    @Column(name = "RANK", length = 4, columnDefinition = "INT DEFAULT 0")
    private Integer rank;

    // 工匠星级（初步，每次订单评价完之后计算）
    @Column(name = "STAR_LEVEL", length = 4, columnDefinition = "INT DEFAULT 0")
    private Integer starLevel;

    // 当期排名
    @Column(name = "PERIOD_RANK", length = 4, columnDefinition = "INT DEFAULT 0")
    private Integer periodRank;
    
    // 当期收入
    @Column(name = "PERIOD_PROFIT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal periodProfit;
    
    // 往期排名
    @Column(name = "PAST_RANK", length = 4, columnDefinition = "INT DEFAULT 0")
    private Integer pastRank;

    // 累计接单数
    @Column(name = "TOTAL_INDENT", length = 4, columnDefinition = "INT DEFAULT 0")
    private Integer totalIndent;

    // 累计收入
    @Column(name = "TOTAL_PROFIT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal totalProfit;

    // 当前可用余额
    @Column(name = "BAL_AMT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal balAmt;

    // 当前冻结余额
    @Column(name = "DEPOSIT_AMT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal depositAmt;

    // 信用分
    @Column(name = "CREDIT_SCORE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal creditScore;

    // 交易密码
    @Column(name = "TRADE_PWD", length = 100, columnDefinition = "VARCHAR(100) DEFAULT ''")
    private String tradePwd;

    // 限制接单时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ASTRICT_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date astrictDate;

    @Transient
    private Account Account;

    @Transient
    private String statusDisp;

    @Transient
    private String roleStatusDisp;

    @Transient
    private String managerIdDisp;

    @Transient
    private String serviceRegion;

    // 用于审核工人时展示工人服务种类
    @Transient
    private String serviceType;

    // 用于审核工人时展示工人技能工种
    @Transient
    private String serviceCraft;

    @Transient // 名字
    private String name1;

    @Transient // 头像
    private String avatar;

    @Transient // 驾驶证
    private String licence;

    @Transient // 行驶证
    private String licence1;

    public Worker() {
        this.code1 = DEF_STRING;
        this.accountId = DEF_STRING;
        this.holdWay = DEF_STRING;
        this.vehicle = DEF_STRING;
        this.vehicleStatus = DEF_STRING;
        this.carNumber = DEF_STRING;
        this.drivingImg = DEF_STRING;
        this.vehicleImg = DEF_STRING;
        this.managerId = DEF_STRING;
        this.managerIdea = DEF_STRING;
        this.registrationId = DEF_STRING;
        this.status = DEF_STRING;
        this.roleStatus = DEF_STRING;
        this.serviceCategory = DEF_STRING;
        this.skillType = DEF_STRING;
        this.serviceProvince = DEF_STRING;
        this.serviceCity = DEF_STRING;
        this.serviceCounty = DEF_STRING;
        this.verifier = DEF_STRING;
        this.verifierId = DEF_STRING;
        this.bankId = DEF_STRING;
        this.remarks = DEF_STRING;
        this.rank = DEF_INTEGER;
        this.starLevel = DEF_INTEGER;
        this.periodRank = DEF_INTEGER;
        this.pastRank = DEF_INTEGER;
        this.totalIndent = DEF_INTEGER;
        this.totalProfit = DEF_DECIMAL;
        this.balAmt = DEF_DECIMAL;
        this.depositAmt = DEF_DECIMAL;
        this.creditScore = DEF_DECIMAL;
        this.tradePwd = DEF_STRING;
        this.astrictDate = DEF_DATE;
        this.periodProfit = DEF_DECIMAL;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getHoldWay() {
        return holdWay;
    }

    public void setHoldWay(String holdWay) {
        this.holdWay = holdWay;
    }

    public String getHoldWayDisp() {
        return holdWayDisp;
    }

    public void setHoldWayDisp(String holdWayDisp) {
        this.holdWayDisp = holdWayDisp;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getVehicleDisp() {
        return vehicleDisp;
    }

    public void setVehicleDisp(String vehicleDisp) {
        this.vehicleDisp = vehicleDisp;
    }

    public String getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(String vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public String getVehicleStatusDisp() {
        return vehicleStatusDisp;
    }

    public void setVehicleStatusDisp(String vehicleStatusDisp) {
        this.vehicleStatusDisp = vehicleStatusDisp;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getDrivingImg() {
        return drivingImg;
    }

    public void setDrivingImg(String drivingImg) {
        this.drivingImg = drivingImg;
    }

    public String getVehicleImg() {
        return vehicleImg;
    }

    public void setVehicleImg(String vehicleImg) {
        this.vehicleImg = vehicleImg;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerIdea() {
        return managerIdea;
    }

    public void setManagerIdea(String managerIdea) {
        this.managerIdea = managerIdea;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(String roleStatus) {
        this.roleStatus = roleStatus;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public String getSkillType() {
        return skillType;
    }

    public void setSkillType(String skillType) {
        this.skillType = skillType;
    }

    public String getServiceProvince() {
        return serviceProvince;
    }

    public void setServiceProvince(String serviceProvince) {
        this.serviceProvince = serviceProvince;
    }

    public String getServiceCity() {
        return serviceCity;
    }

    public void setServiceCity(String serviceCity) {
        this.serviceCity = serviceCity;
    }

    public String getServiceCounty() {
        return serviceCounty;
    }

    public void setServiceCounty(String serviceCounty) {
        this.serviceCounty = serviceCounty;
    }

    public String getVerifier() {
        return verifier;
    }

    public void setVerifier(String verifier) {
        this.verifier = verifier;
    }

    public Date getVerifierDate() {
        return verifierDate;
    }

    public void setVerifierDate(Date verifierDate) {
        this.verifierDate = verifierDate;
    }

    public String getVerifierId() {
        return verifierId;
    }

    public void setVerifierId(String verifierId) {
        this.verifierId = verifierId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(Integer starLevel) {
        this.starLevel = starLevel;
    }

    public BigDecimal getPeriodProfit() {
        return periodProfit;
    }

    public void setPeriodProfit(BigDecimal periodProfit) {
        this.periodProfit = periodProfit;
    }

    public Integer getPastRank() {
        return pastRank;
    }

    public void setPastRank(Integer pastRank) {
        this.pastRank = pastRank;
    }

    public Integer getPeriodRank() {
        return periodRank;
    }

    public void setPeriodRank(Integer periodRank) {
        this.periodRank = periodRank;
    }

    public Integer getTotalIndent() {
        return totalIndent;
    }

    public void setTotalIndent(Integer totalIndent) {
        this.totalIndent = totalIndent;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public BigDecimal getBalAmt() {
        return balAmt;
    }

    public void setBalAmt(BigDecimal balAmt) {
        this.balAmt = balAmt;
    }

    public BigDecimal getDepositAmt() {
        return depositAmt;
    }

    public void setDepositAmt(BigDecimal depositAmt) {
        this.depositAmt = depositAmt;
    }

    public BigDecimal getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(BigDecimal creditScore) {
        this.creditScore = creditScore;
    }

    public String getTradePwd() {
        return tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }

    public Date getAstrictDate() {
        return astrictDate;
    }

    public void setAstrictDate(Date astrictDate) {
        this.astrictDate = astrictDate;
    }

    public Account getAccount() {
        return Account;
    }

    public void setAccount(Account account) {
        Account = account;
    }

    public String getStatusDisp() {
        return statusDisp;
    }

    public void setStatusDisp(String statusDisp) {
        this.statusDisp = statusDisp;
    }

    public String getRoleStatusDisp() {
        return roleStatusDisp;
    }

    public void setRoleStatusDisp(String roleStatusDisp) {
        this.roleStatusDisp = roleStatusDisp;
    }

    public String getManagerIdDisp() {
        return managerIdDisp;
    }

    public void setManagerIdDisp(String managerIdDisp) {
        this.managerIdDisp = managerIdDisp;
    }

    public String getServiceRegion() {
        return serviceRegion;
    }

    public void setServiceRegion(String serviceRegion) {
        this.serviceRegion = serviceRegion;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceCraft() {
        return serviceCraft;
    }

    public void setServiceCraft(String serviceCraft) {
        this.serviceCraft = serviceCraft;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getLicence1() {
        return licence1;
    }

    public void setLicence1(String licence1) {
        this.licence1 = licence1;
    }

}