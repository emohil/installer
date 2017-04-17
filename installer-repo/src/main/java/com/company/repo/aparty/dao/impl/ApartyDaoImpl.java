package com.company.repo.aparty.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.aparty.Aparty;
import com.company.repo.aparty.dao.ApartyDao;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;

@Repository(ApartyDao.BEAN_NAME)
public class ApartyDaoImpl extends StringIdBaseDaoImpl<Aparty> implements ApartyDao {
    
}
