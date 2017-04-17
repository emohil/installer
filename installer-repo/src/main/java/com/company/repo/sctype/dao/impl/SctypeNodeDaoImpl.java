package com.company.repo.sctype.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.sctype.SctypeNode;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.sctype.dao.SctypeNodeDao;


@Repository(SctypeNodeDao.BEAN_NAME)
public class SctypeNodeDaoImpl extends StringIdBaseDaoImpl<SctypeNode> implements SctypeNodeDao {

}
