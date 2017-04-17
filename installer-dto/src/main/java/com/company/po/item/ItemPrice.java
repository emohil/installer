package com.company.po.item;




import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.company.api.fw.DictCodes;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.company.api.fw.TransformField;
import com.company.po.base.StringIdPo;

@Entity
@Table(name = "ZL_ITEM_PRICE")
public class ItemPrice extends StringIdPo {

    /**
     * 项目报价表
     */
    private static final long serialVersionUID = 1L;
    
    @JsonProperty//项目ID
    @Column(name = "ITEM_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String itemId;
    
    @JsonProperty//服务内容ID
    @Column(name = "SERVE_CONTENT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String serveContentId;
    
    //服务内容类型
    @Column(name = "TYPE_NAME", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String typeName;
    
    //服务内容类别
    @Column(name = "SORT_NAME", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String sortName;
    
    //服务内容名称
    @Column(name = "NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String name1;
    
    //服务内容单位
    @TransformField(groupCode = DictCodes.UOM)
    @Column(name = "UNIT", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String unit;
    
    //基础报价
    @Column(name = "BASE_PRICE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal basePrice;
    
    //计算后的报价
    @Column(name = "NUMERATION_PRICE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal numerationPrice;
    
    //实际报价
    @Column(name = "ACTUAL_PRICE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal actualPrice;
    
    //城市加价率
    @Column(name = "CITY_REBATE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal cityRebate;
    
    //地区加价率
    @Column(name = "AREA_REBATE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal areaRebate;

    //甲方加价率
    @Column(name = "APARTY_REBATE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal apartyRebate;
    
    //项目加价率
    @Column(name = "ITEM_REBATE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal itemRebate;
    
    //工人服务费比例
    @Column(name = "WORKER_RATE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal workerRate;
    
    //经理人佣金比例
    @Column(name = "MANAGER_RATE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal managerRate;
    
    //毛利率
    @Column(name = "PROFIT_RATE", precision = 11, scale = 3, columnDefinition = "DECIMAL(11, 3) DEFAULT 0")
    private BigDecimal profitRate;
    
    @Transient
    private String unitDisp;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getServeContentId() {
        return serveContentId;
    }

    public void setServeContentId(String serveContentId) {
        this.serveContentId = serveContentId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
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

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
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

    public BigDecimal getCityRebate() {
        return cityRebate;
    }

    public void setCityRebate(BigDecimal cityRebate) {
        this.cityRebate = cityRebate;
    }

    public BigDecimal getAreaRebate() {
        return areaRebate;
    }

    public void setAreaRebate(BigDecimal areaRebate) {
        this.areaRebate = areaRebate;
    }

    public BigDecimal getApartyRebate() {
        return apartyRebate;
    }

    public void setApartyRebate(BigDecimal apartyRebate) {
        this.apartyRebate = apartyRebate;
    }

    public BigDecimal getItemRebate() {
        return itemRebate;
    }

    public void setItemRebate(BigDecimal itemRebate) {
        this.itemRebate = itemRebate;
    }

    public BigDecimal getWorkerRate() {
        return workerRate;
    }

    public void setWorkerRate(BigDecimal workerRate) {
        this.workerRate = workerRate;
    }

    public BigDecimal getManagerRate() {
        return managerRate;
    }

    public void setManagerRate(BigDecimal managerRate) {
        this.managerRate = managerRate;
    }

    public BigDecimal getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(BigDecimal profitRate) {
        this.profitRate = profitRate;
    }

    public String getUnitDisp() {
        return unitDisp;
    }

    public void setUnitDisp(String unitDisp) {
        this.unitDisp = unitDisp;
    }

}