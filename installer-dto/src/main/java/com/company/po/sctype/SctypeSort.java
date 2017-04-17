package com.company.po.sctype;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.company.po.base.OrderPo;

/***
 * 服务类别表
 * 
 * @author liurengjie
 *
 */
@Entity
@Table(name = "ZL_SCTYPE_SORT", uniqueConstraints = @UniqueConstraint(columnNames = { "CODE1", "SCTYPE_ID" }) )
public class SctypeSort extends OrderPo {

    private static final long serialVersionUID = 1L;

    // 服务类别编码
    @Column(name = "CODE1", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String code1;

    // 服务类型ID
    @Column(name = "SCTYPE_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String sctypeId;

    // 类别内容
    @Column(name = "NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String name1;

    @Transient
    private Sctype sctype;

    @Transient
    private List<SctypeContent> contentList;

    public List<SctypeContent> getContentList() {
        return contentList;
    }

    public void setContentList(List<SctypeContent> contentList) {
        this.contentList = contentList;
    }

    public Sctype getSctype() {
        return sctype;
    }

    public void setSctype(Sctype sctype) {
        this.sctype = sctype;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getSctypeId() {
        return sctypeId;
    }

    public void setSctypeId(String sctypeId) {
        this.sctypeId = sctypeId;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

}
