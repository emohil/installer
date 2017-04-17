package com.company.repo.indent.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.indent.IndentNodeStepItem;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.indent.dao.IndentNodeStepItemDao;

@Repository(IndentNodeStepItemDao.BEAN_NAME)
public class IndentNodeStepItemDaoImpl extends
        StringIdBaseDaoImpl<IndentNodeStepItem> implements IndentNodeStepItemDao {

}
