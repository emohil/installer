package com.company.repo.district.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.district.Prov;
import com.company.repo.district.dao.ProvDao;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;


@Repository(ProvDao.BEAN_NAME)
public class ProvDaoImpl extends StringIdBaseDaoImpl<Prov> implements ProvDao {

}
