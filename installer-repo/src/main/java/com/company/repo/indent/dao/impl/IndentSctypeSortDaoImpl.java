package com.company.repo.indent.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.indent.IndentSort;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.indent.dao.IndentSctypeSortDao;


@Repository(IndentSctypeSortDao.BEAN_NAME)
public class IndentSctypeSortDaoImpl extends StringIdBaseDaoImpl<IndentSort> implements IndentSctypeSortDao {

}
