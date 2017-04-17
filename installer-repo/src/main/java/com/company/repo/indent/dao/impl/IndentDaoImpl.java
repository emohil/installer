package com.company.repo.indent.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.indent.Indent;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.indent.dao.IndentDao;


@Repository(IndentDao.BEAN_NAME)
public class IndentDaoImpl extends StringIdBaseDaoImpl<Indent> implements IndentDao {

}
