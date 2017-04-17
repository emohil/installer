package com.company.po.sctype;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.company.api.fw.DictCodes;
import com.company.api.fw.TransformField;
import com.company.po.base.StringIdPo;

/***
 * 服务内容表
 * 
 * @author liurengjie
 *
 */
@Entity
@Table(name = "ZL_SCTYPE_CONTENT", uniqueConstraints = @UniqueConstraint(columnNames = {"CODE1", "SCTYPE_SORT_ID"}))
public class SctypeContent extends StringIdPo {

    private static final long serialVersionUID = 1L;

    // 内容编码
    @Column(name = "CODE1", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String code1;

    // 服务类别ID
    @Column(name = "SCTYPE_SORT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String sctypeSortId;
    

    // 内容内容明细
    @Column(name = "DESC1", length = 500, columnDefinition = "VARCHAR(500) DEFAULT ''")
    private String desc1;
    
    // 内容单位
    @TransformField(groupCode = DictCodes.UOM)
    @Column(name = "UNIT", length = 10, columnDefinition = "VARCHAR(10) DEFAULT ''")
    private String unit;
    
    @Transient
    private String unitDisp;
    
    // 基本报价
    @Column(name = "BASE_QUOTE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal baseQuote;
    
    @Transient
    private SctypeSort sctypeSort;

    @Transient
    private BigDecimal numerationPrice;
    
    @Transient
    private BigDecimal actualPrice;
    
    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getSctypeSortId() {
        return sctypeSortId;
    }

    public void setSctypeSortId(String sctypeSortId) {
        this.sctypeSortId = sctypeSortId;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
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

    public BigDecimal getBaseQuote() {
        return baseQuote;
    }

    public void setBaseQuote(BigDecimal baseQuote) {
        this.baseQuote = baseQuote;
    }

    public SctypeSort getSctypeSort() {
        return sctypeSort;
    }

    public void setSctypeSort(SctypeSort sctypeSort) {
        this.sctypeSort = sctypeSort;
    }

    public BigDecimal getNumerationPrice() {
        return numerationPrice;
    }

    public void setNumerationPrice(BigDecimal numerationPrice) {
        this.numerationPrice = numerationPrice;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }
    
}
