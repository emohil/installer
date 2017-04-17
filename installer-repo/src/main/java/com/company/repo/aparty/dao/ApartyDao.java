package com.company.repo.aparty.dao;

import com.company.po.aparty.Aparty;
import com.company.repo.fw.dao.StringIdBaseDao;

public interface ApartyDao extends StringIdBaseDao<Aparty> {

    String BEAN_NAME = "apartyDao";
}
