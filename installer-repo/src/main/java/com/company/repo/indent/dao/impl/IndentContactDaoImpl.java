package com.company.repo.indent.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.indent.IndentContact;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.indent.dao.IndentContactDao;


@Repository(IndentContactDao.BEAN_NAME)
public class IndentContactDaoImpl extends StringIdBaseDaoImpl<IndentContact> implements IndentContactDao {

}
