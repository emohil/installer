package com.company.repo.indent.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.indent.IndentContent;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.indent.dao.IndentContentDao;


@Repository(IndentContentDao.BEAN_NAME)
public class IndentContentDaoImpl extends StringIdBaseDaoImpl<IndentContent> implements IndentContentDao {

}
