package com.company.api.district.service;

import java.util.List;
import java.util.Map;

import com.company.dto.city.CityData;
import com.company.po.district.Area;
import com.company.api.fw.service.StringIdBaseService;

public interface AreaService extends StringIdBaseService<Area> {
    
    String BEAN_NAME = "areaService";
    
    
    List<Area> queryByCityId(String apartyId);

    /**
     * 已启用的区域数据，按城市分组.
     * 
     * @return
     */
    Map<String, List<CityData>> city2Areas();
    
    Area findAreaByCode1(String code1);
}