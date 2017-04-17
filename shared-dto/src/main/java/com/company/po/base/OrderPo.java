package com.company.po.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.CompareToBuilder;

@MappedSuperclass
public abstract class OrderPo extends StringIdPo implements Comparable<OrderPo> {

    private static final long serialVersionUID = 1L;

    public static final String ORDER_PROPERTY_NAME = "orders";

    @Column(name = "ORDERS", columnDefinition = "INT DEFAULT 0")
    private Integer orders;

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    @Override
    public int compareTo(OrderPo o) {
        return new CompareToBuilder() //
                .append(getOrders(), o.getOrders()) //
                .append(getId(), o.getId()) //
                .toComparison();
    }

}