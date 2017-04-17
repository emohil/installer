package com.company.api.fw.service;

import java.util.List;

import com.company.dto.Order;

public interface PagerService<SF, BEAN> {

    List<BEAN> list(SF sf, int start, int limit, List<Order> orders);

    int count(SF sf);
}
