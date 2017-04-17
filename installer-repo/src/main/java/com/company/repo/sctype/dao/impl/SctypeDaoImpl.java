package com.company.repo.sctype.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.sctype.Sctype;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.sctype.dao.SctypeDao;


@Repository(SctypeDao.BEAN_NAME)
public class SctypeDaoImpl extends StringIdBaseDaoImpl<Sctype> implements SctypeDao {

}
