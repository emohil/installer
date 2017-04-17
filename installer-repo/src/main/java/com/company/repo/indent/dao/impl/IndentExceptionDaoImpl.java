package com.company.repo.indent.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.indent.IndentException;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.indent.dao.IndentExceptionDao;


@Repository(IndentExceptionDao.BEAN_NAME)
public class IndentExceptionDaoImpl extends StringIdBaseDaoImpl<IndentException> implements IndentExceptionDao {

}
