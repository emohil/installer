package com.company.repo.indent.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.indent.IndentEvaluate;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.indent.dao.IndentEvaluateDao;


@Repository(IndentEvaluateDao.BEAN_NAME)
public class IndentEvaluateDaoImpl extends StringIdBaseDaoImpl<IndentEvaluate> implements IndentEvaluateDao {

}
