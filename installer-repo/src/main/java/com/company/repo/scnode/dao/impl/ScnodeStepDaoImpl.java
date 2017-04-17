package com.company.repo.scnode.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.scnode.ScnodeStep;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.scnode.dao.ScnodeStepDao;

@Repository(ScnodeStepDao.BEAN_NAME)
public class ScnodeStepDaoImpl extends StringIdBaseDaoImpl<ScnodeStep> implements ScnodeStepDao {

    
}
