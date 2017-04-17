package com.company.po.sctype;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.company.po.base.OrderPo;

/***
 * 服务类别节点表
 * 
 * @author liurengjie
 *
 */
@Entity
@Table(name = "ZL_SCTYPE_NODE")
public class SctypeNode extends OrderPo {

    private static final long serialVersionUID = 1L;

    // 服务类型ID
    @Column(name = "SCTYPE_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String sctypeId;

    // 节点ID
    @Column(name = "SCNODE_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String scnodeId;

    public String getSctypeId() {
        return sctypeId;
    }

    public void setSctypeId(String sctypeId) {
        this.sctypeId = sctypeId;
    }

    public String getScnodeId() {
        return scnodeId;
    }

    public void setScnodeId(String scnodeId) {
        this.scnodeId = scnodeId;
    }
}