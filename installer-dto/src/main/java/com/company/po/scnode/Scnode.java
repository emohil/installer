package com.company.po.scnode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.company.po.base.OrderPo;

/**
 * SERVICE NODE
 * 服务节点 
 * @author lihome
 *
 */
@Entity
@Table(name = "ZL_SCNODE")
public class Scnode extends OrderPo {

    private static final long serialVersionUID = 1L;

    @Column(name = "CODE1", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String code1;
    
    // 节点名称
    @Column(name = "NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String name1;

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
}
