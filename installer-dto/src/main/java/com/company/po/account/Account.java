package com.company.po.account;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.company.po.mgr.Manager;
import com.company.po.wk.Worker;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.company.context.ContextBean;
import com.company.po.base.StringIdPo;

@Entity
@Table(name = "ZL_ACCOUNT", uniqueConstraints = @UniqueConstraint(columnNames = "ACCOUNT") )
public class Account extends StringIdPo implements ContextBean<String> {

    /** 
     * 
     */
    private static final long serialVersionUID = 1L;

    // 账号
    @Column(name = "ACCOUNT", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String account;

    // 手机号
    @JsonProperty
    @Column(name = "MOBILE", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String mobile;

    @JsonProperty
    @Column(name = "PWD", length = 100, columnDefinition = "VARCHAR(100) DEFAULT ''", nullable = false)
    private String pwd;

    // 身份证照片
    @JsonProperty
    @Column(name = "ID_IMG_URL", length = 255, columnDefinition = "VARCHAR(255) default ''")
    private String idImgUrl;

    // 头像
    @JsonProperty
    @Column(name = "AVATAR", length = 255, columnDefinition = "VARCHAR(255) default ''")
    private String avatar;

    // 姓名
    @JsonProperty
    @Column(name = "NAME1", length = 10, columnDefinition = "VARCHAR(10) DEFAULT ''")
    private String name1;

    // 性别
    @JsonProperty
    @Column(name = "SEX", length = 2, columnDefinition = "VARCHAR(2) DEFAULT ''")
    private String sex;

    // 身份证号
    @JsonProperty
    @Column(name = "ID_NUM", length = 20, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String idNum;

    // 生日
    @Column(name = "BIRTH_DAY", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date birthDay;

    // 现住址
    @Column(name = "ADDR1", length = 200, columnDefinition = "VARCHAR(200) DEFAULT ''")
    private String addr1;

    // 楼号门牌号
    @Column(name = "BUILD_NUM", length = 200, columnDefinition = "VARCHAR(200) DEFAULT ''")
    private String buildNum;

    // 工人状态(ROLE_STATUS)
    @JsonProperty
    @Column(name = "WORKER_TYPE", length = 2, columnDefinition = "INT DEFAULT 0")
    private Integer workerType;

    // 工人ID
    @JsonProperty
    @Column(name = "WORKER_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String workerId;

    // 经理人状态(ROLE_STATUS)
    @JsonProperty
    @Column(name = "MANAGER_TYPE", length = 2, columnDefinition = "INT DEFAULT 0")
    private Integer managerType;

    // 经理人ID
    @JsonProperty
    @Column(name = "MANAGER_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String managerId;

    // 账号状态(ROLE_STATUS)
    @JsonProperty
    @Column(name = "STATUS", length = 10, columnDefinition = "VARCHAR(10) DEFAULT ''")
    private String status;
    
    // 最后一次登录账户类型(LAST_LOGIN_TYPE)
    @Column(name = "LAST_LOGIN_TYPE", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String lastLoginType;

    // 最后一次登录时间
    @Temporal(TemporalType.DATE)
    @Column(name = "LAST_LOGIN_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date lastLoginDate;
    
    // registrationIds
    @JsonProperty
    @Column(name = "REGISTRATION_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String registrationId;
    
    @Transient
    @JsonProperty
    private Worker worker;

    @Transient
    @JsonProperty
    private Manager manager;

    @Transient
    private String idImg;

    @Transient // 存放头像路径
    private String avatarImg;

    public Account() {
        this.account = DEF_STRING;
        this.mobile = DEF_STRING;
        this.pwd = DEF_STRING;
        this.idImgUrl = DEF_STRING;
        this.avatar = DEF_STRING;
        this.name1 = DEF_STRING;
        this.sex = DEF_STRING;
        this.idNum = DEF_STRING;
        this.birthDay = DEF_DATE;
        this.addr1 = DEF_STRING;
        this.buildNum = DEF_STRING;
        this.workerType = DEF_INTEGER;
        this.workerId = DEF_STRING;
        this.managerType = DEF_INTEGER;
        this.managerId = DEF_STRING;
        this.status = DEF_STRING;
        this.registrationId = DEF_STRING;
        this.lastLoginType = DEF_STRING;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getIdImgUrl() {
        return idImgUrl;
    }

    public void setIdImgUrl(String idImgUrl) {
        this.idImgUrl = idImgUrl;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getBuildNum() {
        return buildNum;
    }

    public void setBuildNum(String buildNum) {
        this.buildNum = buildNum;
    }

    public Integer getWorkerType() {
        return workerType;
    }

    public void setWorkerType(Integer workerType) {
        this.workerType = workerType;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public Integer getManagerType() {
        return managerType;
    }

    public void setManagerType(Integer managerType) {
        this.managerType = managerType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginType() {
        return lastLoginType;
    }

    public void setLastLoginType(String lastLoginType) {
        this.lastLoginType = lastLoginType;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    @Override
    public String getKeyId() {
        return getId();
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }

    public String getIdImg() {
        return idImg;
    }

    public void setIdImg(String idImg) {
        this.idImg = idImg;
    }

    
    public static void main(String[] args) {
        new Account();
    }
}