package com.company.repo.district.dao;

import com.company.po.district.City;
import com.company.repo.fw.dao.StringIdBaseDao;

public interface CityDao extends StringIdBaseDao<City> {

    String BEAN_NAME = "cityDao";
}
