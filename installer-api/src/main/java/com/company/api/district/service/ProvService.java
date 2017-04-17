package com.company.api.district.service;

import java.util.List;

import com.company.dto.city.CityData;
import com.company.po.district.Prov;
import com.company.api.fw.service.StringIdBaseService;
import com.company.dto.Order;

public interface ProvService extends StringIdBaseService<Prov> {
    
    String BEAN_NAME = "provService";
    
    List<Prov> list(Prov sf, int start, int limit, List<Order> orders);

    int count(Prov sf);
    
    List<Prov> queryProvList();
    
    
    List<CityData> queryEnabledProvs();

}