package com.company.repo.sctype.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.sctype.SctypeSort;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.sctype.dao.SctypeSortDao;


@Repository(SctypeSortDao.BEAN_NAME)
public class SctypeSortDaoImpl extends StringIdBaseDaoImpl<SctypeSort> implements SctypeSortDao {

}
