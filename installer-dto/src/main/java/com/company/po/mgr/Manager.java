package com.company.po.mgr;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.company.api.fw.EnumCodes;
import com.company.po.account.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.company.api.fw.TransformField;
import com.company.po.base.StringIdPo;

@Entity
@Table(name = "ZL_MANAGER", uniqueConstraints = @UniqueConstraint(columnNames = "CODE1"))
public class Manager extends StringIdPo {
    
    private static final long serialVersionUID = 1L;
    
    // 经理人工号
    @Column(name = "CODE1", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String code1;

    // Account账号ID
    @Column(name = "ACCOUNT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String accountId;

    // 交通工具
    @Column(name = "VEHICLE", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String vehicle;
    
    // 营业执照片
    @Column(name = "CHARTER_IMG", length = 255, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String charterImg;
    
    // 服务区域（市）
    @Column(name = "SERVE_CITY", length =32 , columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String serveCity;
    
    // 是否为小助手(0 不是  1 是)
    @Column(name = "IS_HELPER", length =11 , columnDefinition = "INT DEFAULT 0")
    private Integer isHelper;

    // 组织机构代码证
    @Column(name = "ORG_CODE_IMG", length = 255, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String orgCodeImg;
    
    // 税务登记证
    @Column(name = "TAX_IMG", length = 255, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String taxImg;

    // 审核状态
    @TransformField(groupCode = EnumCodes.CHECK_STATUS)
    @Column(name = "STATUS", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String status;

    @Transient
    private String statusDisp;

    // 角色状态
    @TransformField(groupCode = EnumCodes.ACCOUNT_STATUS)
    @Column(name = "ROLE_STATUS", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String roleStatus;

    @Transient
    private String roleStatusDisp;
    
    //等级（初步，每次订单评价完之后计算）
    @Column(name = "RANK", length = 4, columnDefinition = "INT DEFAULT 0")
    private Integer rank;
    
    // 星级
    @JsonProperty
    @Column(name = "STARS", precision = 11, scale = 3, columnDefinition = "DOUBLE(11, 3) DEFAULT 0")
    private double stars;

    // KPI
    @Column(name = "KPI", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String kpi;

    // 审核人
    @Column(name = "VERIFIER", length = 100, columnDefinition = "VARCHAR(100) DEFAULT ''")
    private String verifier;

    // 审核人 ID
    @Column(name = "VERIFIER_ID", length = 100, columnDefinition = "VARCHAR(100) DEFAULT ''")
    private String verifierId;

    // 关联银行卡 ID
    @Column(name = "BANK_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String bankId;
    
    //备注（用于存放审核反馈信息）
    @Column(name = "REMARKS", length = 500, columnDefinition = "VARCHAR(500) DEFAULT ''")
    private String remarks;
    
    //当期接单数
    @Column(name = "PERIOD_INDENT", length = 4, columnDefinition = "INT DEFAULT 0")
    private Integer periodIndent;
    
    //当期收入
    @Column(name = "PERIOD_PROFIT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal periodProfit;
    
    //当期排名
    @Column(name = "PERIOD_RANK", length = 4, columnDefinition = "INT DEFAULT 0")
    private Integer periodRank;
    
    //往期排名
    @Column(name = "PAST_RANK", length = 4, columnDefinition = "INT DEFAULT 0")
    private Integer pastRank;
    
    //累计订单数
    @Column(name = "TOTAL_INDENT", length = 11, columnDefinition = "INT DEFAULT 0")
    private Integer totalIndent;
    
    //累计收入
    @Column(name = "TOTAL_PROFIT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal totalProfit;
    
    //当前可用余额
    @Column(name = "BAL_AMT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal balAmt;
    
    //当前冻结余额
    @Column(name = "DEPOSIT_AMT", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal depositAmt;
    
    //信用分
    @Column(name = "CREDIT_SCORE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal creditScore;
    
    //及时交付率
    @Column(name = "TIMELY_PASS", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal timelyPass;
    
    //一次交付成功率
    @Column(name = "ONE_PASS", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal onePass;
    
    //订单消耗率
    @Column(name = "INDENT_EXPEND", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal indentExpend;
    
    //交易密码
    @Column(name = "TRADE_PWD", length = 100, columnDefinition = "VARCHAR(100) DEFAULT ''")
    private String tradePwd;
    
    @Transient
    private Account account;
    
    @Transient
    private String serviceCity;
    
    @Transient
    private String charter;
    
    @Transient
    private String orgCode;
    
    @Transient
    private String tax;
    
    public Manager() {
        this.totalIndent = DEF_INTEGER;
        this.accountId = DEF_STRING;
        this.vehicle = DEF_STRING;
        this.charterImg = DEF_STRING;
        this.serveCity = DEF_STRING;
        this.isHelper = DEF_INTEGER;
        this.orgCodeImg = DEF_STRING;
        this.taxImg = DEF_STRING;
        this.status = DEF_STRING;
        this.roleStatus = DEF_STRING;
        this.stars = DEF_INTEGER;
        this.kpi = DEF_STRING;
        this.verifier = DEF_STRING;
        this.verifierId = DEF_STRING;
        this.bankId = DEF_STRING;
        this.remarks = DEF_STRING;
        this.periodIndent = DEF_INTEGER;
        this.periodProfit = DEF_DECIMAL;
        this.periodRank = DEF_INTEGER;
        this.pastRank = DEF_INTEGER;
        this.totalProfit = DEF_DECIMAL;
        this.balAmt = DEF_DECIMAL;
        this.depositAmt = DEF_DECIMAL;
        this.creditScore = DEF_DECIMAL;
        this.timelyPass = DEF_DECIMAL;
        this.onePass = DEF_DECIMAL;
        this.indentExpend = DEF_DECIMAL;
        this.tradePwd = DEF_STRING;
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

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getCharterImg() {
        return charterImg;
    }

    public void setCharterImg(String charterImg) {
        this.charterImg = charterImg;
    }

    public String getServeCity() {
        return serveCity;
    }

    public void setServeCity(String serveCity) {
        this.serveCity = serveCity;
    }

    public Integer getIsHelper() {
        return isHelper;
    }

    public void setIsHelper(Integer isHelper) {
        this.isHelper = isHelper;
    }

    public String getOrgCodeImg() {
        return orgCodeImg;
    }

    public void setOrgCodeImg(String orgCodeImg) {
        this.orgCodeImg = orgCodeImg;
    }

    public String getTaxImg() {
        return taxImg;
    }

    public void setTaxImg(String taxImg) {
        this.taxImg = taxImg;
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

    public String getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(String roleStatus) {
        this.roleStatus = roleStatus;
    }

    public String getRoleStatusDisp() {
        return roleStatusDisp;
    }

    public void setRoleStatusDisp(String roleStatusDisp) {
        this.roleStatusDisp = roleStatusDisp;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public String getKpi() {
        return kpi;
    }

    public void setKpi(String kpi) {
        this.kpi = kpi;
    }

    public String getVerifier() {
        return verifier;
    }

    public void setVerifier(String verifier) {
        this.verifier = verifier;
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

    public Integer getPeriodIndent() {
        return periodIndent;
    }

    public void setPeriodIndent(Integer periodIndent) {
        this.periodIndent = periodIndent;
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

    public Integer getTotalIndent() {
        return totalIndent;
    }

    public void setTotalIndent(Integer totalIndent) {
        this.totalIndent = totalIndent;
    }

    public Integer getPeriodRank() {
        return periodRank;
    }

    public void setPeriodRank(Integer periodRank) {
        this.periodRank = periodRank;
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

    public BigDecimal getTimelyPass() {
        return timelyPass;
    }

    public void setTimelyPass(BigDecimal timelyPass) {
        this.timelyPass = timelyPass;
    }

    public BigDecimal getOnePass() {
        return onePass;
    }

    public void setOnePass(BigDecimal onePass) {
        this.onePass = onePass;
    }

    public BigDecimal getIndentExpend() {
        return indentExpend;
    }

    public void setIndentExpend(BigDecimal indentExpend) {
        this.indentExpend = indentExpend;
    }

    public String getTradePwd() {
        return tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getServiceCity() {
        return serviceCity;
    }

    public void setServiceCity(String serviceCity) {
        this.serviceCity = serviceCity;
    }

    public String getCharter() {
        return charter;
    }

    public void setCharter(String charter) {
        this.charter = charter;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }
}