package com.company.repo.scnode.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.scnode.Scnode;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.scnode.dao.ScnodeDao;

@Repository(ScnodeDao.BEAN_NAME)
public class ScnodeDaoImpl extends StringIdBaseDaoImpl<Scnode> implements ScnodeDao {

    
}
