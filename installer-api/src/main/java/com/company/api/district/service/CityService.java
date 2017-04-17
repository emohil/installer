package com.company.api.district.service;

import java.util.List;
import java.util.Map;

import com.company.dto.city.CityData;
import com.company.po.district.City;
import com.company.api.fw.service.StringIdBaseService;
import com.company.dto.Order;

public interface CityService extends StringIdBaseService<City> {

    String BEAN_NAME = "cityService";

    List<City> list(City sf, int start, int limit, List<Order> orders);

    int count(City sf);

    List<City> queryByProvId(City sf);

    /**
     * 已启用的城市数据，按省份分组.
     * 
     * @return
     */
    Map<String, List<CityData>> enabledProv2Cities();

    /**
     * 组装好的城市数据，包含省、市、区
     * 
     * @return
     */
    List<CityData> cityData();
    
    void closeCity(City entity);
    
    City findCityByCode1(String code1);

}
