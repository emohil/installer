package com.company.repo.dict.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.dict.DictGroup;
import com.company.repo.dict.dao.DictGroupDao;
import com.company.repo.fw.dao.impl.BaseDaoImpl;

@Repository(DictGroupDao.BEAN_NAME)
public class DictGroupDaoImpl extends BaseDaoImpl<DictGroup, String> implements DictGroupDao {

}