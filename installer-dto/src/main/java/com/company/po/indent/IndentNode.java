package com.company.po.indent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.company.api.fw.EnumCodes;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.company.api.fw.TransformField;
import com.company.po.base.OrderPo;

/**
 * INDENT NODE
 * 订单节点
 * @author kouwl
 *
 * @Date 2016年2月26日
 */
@Entity
@Table(name = "ZL_INDENT_NODE")
public class IndentNode  extends OrderPo {

    private static final long serialVersionUID = 1L;

    // 订单ID
    @Column(name = "INDENT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String indentId;
    
    //CODE1
    @Column(name = "CODE1", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String code1;
    
    // 节点名称
    @Column(name = "NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String name1;
    
    //节点执行状态
    @JsonProperty
    @TransformField(groupCode = EnumCodes.INDENT_EXECUTE_STATUS)
    @Column(name = "NODE_EXECUTE_STATUS", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String nodeExecuteStatus;
    
    @Transient
    @JsonProperty
    private String nodeExecuteStatusDisp;
    

    public String getNodeExecuteStatus() {
        return nodeExecuteStatus;
    }

    public void setNodeExecuteStatus(String nodeExecuteStatus) {
        this.nodeExecuteStatus = nodeExecuteStatus;
    }

    public String getNodeExecuteStatusDisp() {
        return nodeExecuteStatusDisp;
    }

    public void setNodeExecuteStatusDisp(String nodeExecuteStatusDisp) {
        this.nodeExecuteStatusDisp = nodeExecuteStatusDisp;
    }

    public String getIndentId() {
        return indentId;
    }

    public void setIndentId(String indentId) {
        this.indentId = indentId;
    }

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
