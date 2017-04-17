package com.company.api.aparty.service;

import java.util.List;

import com.company.po.aparty.ApartyTrade;
import com.company.sf.aparty.ApartyTradeSf;
import com.company.api.fw.service.StringIdBaseService;
import com.company.dto.Order;

public interface ApartyTradeService extends StringIdBaseService<ApartyTrade> {
    
    String BEAN_NAME = "apartyTradeService";

    List<ApartyTrade> list(ApartyTradeSf sf, int start, int limit, List<Order> orders);

    int count(ApartyTradeSf sf);
    
    void deleteByApartyId(String apartyId);
}