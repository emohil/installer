package com.company.po.indent;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.company.api.fw.DictCodes;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.company.api.fw.TransformField;
import com.company.po.base.StringIdPo;

/***
 * 订单内容报价表
 * 
 * @author liurengjie
 *
 */
@Entity
@Table(name = "ZL_INDENT_CONTENT")
public class IndentContent extends StringIdPo {

    private static final long serialVersionUID = 1L;

    // 订单ID
    @JsonProperty
    @Column(name = "INDENT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String indentId;

    // 服务编码 CODE1
    @JsonProperty
    @Column(name = "CODE1", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String code1;

    // 服务名称 Name1
    @Column(name = "NAME1", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String name1;

    // 单位
    @TransformField(groupCode = DictCodes.UOM)
    @Column(name = "UNIT", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String unit;
    
    @Transient
    private String unitDisp;

    // 数量
    @Column(name = "COUNTS", length = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private double counts;

    // 报价
    @Column(name = "PRICE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal price;

    // 总报价
    @JsonProperty
    @Column(name = "TOTAL", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal total;

    @Transient
    private String checked;
    
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitDisp() {
        return unitDisp;
    }

    public void setUnitDisp(String unitDisp) {
        this.unitDisp = unitDisp;
    }

    public double getCounts() {
        return counts;
    }

    public void setCounts(double counts) {
        this.counts = counts;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

}
