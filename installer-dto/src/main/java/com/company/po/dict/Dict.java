package com.company.po.dict;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.company.po.base.OrderPo;

@Entity
@Table(name = "ZL_DICT", uniqueConstraints = @UniqueConstraint(columnNames = { "GROUP_CODE", "VALUE1" }))
@Access(AccessType.FIELD)
public class Dict extends OrderPo {

    private static final long serialVersionUID = 1L;

    @Column(name = "GROUP_CODE", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String groupCode;

    @Column(name = "VALUE1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''", updatable = false)
    private String value1;

    @Column(name = "TEXT1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String text1;

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }
}