package com.company.repo.sctype.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.sctype.SctypeContent;
import com.company.repo.fw.dao.impl.BaseDaoImpl;
import com.company.repo.sctype.dao.SctypeContentDao;


@Repository(SctypeContentDao.BEAN_NAME)
public class SctypeContentDaoImpl extends BaseDaoImpl<SctypeContent, String> implements SctypeContentDao {

}
