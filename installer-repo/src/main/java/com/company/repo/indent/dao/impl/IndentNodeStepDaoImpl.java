package com.company.repo.indent.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.indent.IndentNodeStep;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.indent.dao.IndentNodeStepDao;

@Repository(IndentNodeStepDao.BEAN_NAME)
public class IndentNodeStepDaoImpl extends StringIdBaseDaoImpl<IndentNodeStep>
        implements IndentNodeStepDao {

}
