package com.company.repo.indent.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.indent.IndentNode;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.indent.dao.IndentNodeDao;

@Repository(IndentNodeDao.BEAN_NAME)
public class IndentNodeDaoImpl extends StringIdBaseDaoImpl<IndentNode> implements IndentNodeDao {

    
}
