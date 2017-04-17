package com.company.repo.district.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.district.City;
import com.company.repo.district.dao.CityDao;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;


@Repository(CityDao.BEAN_NAME)
public class CityDaoImpl extends StringIdBaseDaoImpl<City> implements CityDao {

}
