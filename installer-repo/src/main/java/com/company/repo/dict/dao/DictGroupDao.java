package com.company.repo.dict.dao;

import com.company.po.dict.DictGroup;
import com.company.repo.fw.dao.BaseDao;

public interface DictGroupDao extends BaseDao<DictGroup, String> {

    String BEAN_NAME = "dictGroupDao";
}