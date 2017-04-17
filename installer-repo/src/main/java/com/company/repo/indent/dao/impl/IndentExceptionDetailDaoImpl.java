package com.company.repo.indent.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.indent.IndentExceptionDetail;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.indent.dao.IndentExceptionDetailDao;


@Repository(IndentExceptionDetailDao.BEAN_NAME)
public class IndentExceptionDetailDaoImpl extends StringIdBaseDaoImpl<IndentExceptionDetail> implements IndentExceptionDetailDao {

}
