package com.company.repo.scnode.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.scnode.ScnodeStepItem;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.scnode.dao.ScnodeStepItemDao;

@Repository(ScnodeStepItemDao.BEAN_NAME)
public class ScnodeStepItemDaoImpl extends StringIdBaseDaoImpl<ScnodeStepItem> implements ScnodeStepItemDao {

    
}
