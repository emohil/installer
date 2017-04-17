package com.company.po.sctype;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.company.po.base.OrderPo;
import com.company.util.New;

/***
 * SERVICE TYPE 服务类型表
 * 
 * @author liurengjie
 *
 */
@Entity
@Table(name = "ZL_SCTYPE", uniqueConstraints = @UniqueConstraint(columnNames = "CODE1") )
public class Sctype extends OrderPo {

    private static final long serialVersionUID = 1L;

    // 服务类型编码
    @Column(name = "CODE1", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String code1;

    // 服务类型名称
    @Column(name = "NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String name1;

    @Transient
    private List<SctypeSort> sortList;

    @Transient
    private Map<String, Boolean> nodes = New.hashMap();;

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

    public List<SctypeSort> getSortList() {
        return sortList;
    }

    public void setSortList(List<SctypeSort> sortList) {
        this.sortList = sortList;
    }

    public Map<String, Boolean> getNodes() {
        return nodes;
    }

    public void setNodes(Map<String, Boolean> nodes) {
        this.nodes = nodes;
    }
}