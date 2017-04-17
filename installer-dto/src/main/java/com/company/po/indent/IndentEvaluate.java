package com.company.po.indent;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.TransformField;
import com.company.po.base.StringIdPo;

/**
 * 订单评价表
 * @author kouwl
 *
 * @Date 2016年3月14日
 */

@Entity
@Table(name = "ZL_INDENT_EVALUATE")
public class IndentEvaluate extends StringIdPo {

    private static final long serialVersionUID = 1L;

    // 订单ID
    @Column(name = "INDENT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String indentId;

    // 工匠ID
    @Column(name = "WORKER_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String workerId;
    
    //订单完成情况
    @TransformField(groupCode = EnumCodes.YESNO)
    @Column(name = "WORKDONE", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String workdone;
    
    @Transient
    private String workdoneDisp;
    
    //安裝技能评分
    @Column(name = "SKILL_SCORE", length = 4, columnDefinition = "INT DEFAULT 0")
    private Integer skillScore;
    
    //服务态度评分
    @Column(name = "SERVE_SCORE", length = 4, columnDefinition = "INT DEFAULT 0")
    private Integer serveScore;
    
    //评价内容
    @Column(name = "CONTENT", length = 600, columnDefinition = "VARCHAR(600) DEFAULT ''")
    private String content;
    
    @Transient
    private IndentContact indentContact;
    
    @Transient
    private List<String> imgUrlList;

    public String getIndentId() {
        return indentId;
    }

    public void setIndentId(String indentId) {
        this.indentId = indentId;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getWorkdone() {
        return workdone;
    }

    public void setWorkdone(String workdone) {
        this.workdone = workdone;
    }

    public Integer getSkillScore() {
        return skillScore;
    }

    public void setSkillScore(Integer skillScore) {
        this.skillScore = skillScore;
    }

    public Integer getServeScore() {
        return serveScore;
    }

    public void setServeScore(Integer serveScore) {
        this.serveScore = serveScore;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWorkdoneDisp() {
        return workdoneDisp;
    }

    public void setWorkdoneDisp(String workdoneDisp) {
        this.workdoneDisp = workdoneDisp;
    }

    public IndentContact getIndentContact() {
        return indentContact;
    }

    public void setIndentContact(IndentContact indentContact) {
        this.indentContact = indentContact;
    }

    public List<String> getImgUrlList() {
        return imgUrlList;
    }

    public void setImgUrlList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

}