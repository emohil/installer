package com.company.po.indent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.company.po.base.StringIdPo;

/***
 * 订单类别表
 * 
 * @author liurengjie
 *
 */
@Entity
@Table(name = "ZL_INDENT_SORT")
public class IndentSort extends StringIdPo {

    private static final long serialVersionUID = 1L;

    // 类别代码
    @Column(name = "CODE1", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String code1;

    // 类别名称
    @Column(name = "NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String name1;

    // 订单ID
    @Column(name = "INDENT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String indentId;
    
    @Column(name = "SERVE_TYPE", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String serveType;

    // 服务类别ID
    @Column(name = "SCTYPE_SORT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String sctypeSortId;
    
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

    public String getIndentId() {
        return indentId;
    }

    public void setIndentId(String indentId) {
        this.indentId = indentId;
    }

    public String getSctypeSortId() {
        return sctypeSortId;
    }

    public void setSctypeSortId(String sctypeSortId) {
        this.sctypeSortId = sctypeSortId;
    }

    public String getServeType() {
        return serveType;
    }

    public void setServeType(String serveType) {
        this.serveType = serveType;
    }

}
