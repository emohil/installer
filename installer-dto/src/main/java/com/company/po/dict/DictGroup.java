package com.company.po.dict;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.company.po.base.StringIdPo;

@Entity
@Table(name = "ZL_DICT_GROUP", uniqueConstraints = @UniqueConstraint(columnNames = "CODE1"))
@Access(AccessType.FIELD)
public class DictGroup extends StringIdPo {

    private static final long serialVersionUID = 1L;

    @Column(name = "CODE1", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String code1;

    @Column(name = "NAME1", length = 100, columnDefinition = "VARCHAR(100) DEFAULT ''")
    private String name1;

    @Column(name = "DESC1", length = 255, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String desc1;

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

}