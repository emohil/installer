package com.company.repo.indent.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.indent.IndentFreight;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.indent.dao.IndentFreightDao;


@Repository(IndentFreightDao.BEAN_NAME)
public class IndentFreightDaoImpl extends StringIdBaseDaoImpl<IndentFreight> implements IndentFreightDao {

}
