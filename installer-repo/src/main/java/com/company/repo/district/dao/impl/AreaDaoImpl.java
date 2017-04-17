package com.company.repo.district.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.district.Area;
import com.company.repo.district.dao.AreaDao;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;


@Repository(AreaDao.BEAN_NAME)
public class AreaDaoImpl extends StringIdBaseDaoImpl<Area> implements AreaDao {

}
