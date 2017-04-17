package com.company.repo.indent.dao;

import com.company.po.indent.Indent;
import com.company.repo.fw.dao.StringIdBaseDao;

public interface IndentDao extends StringIdBaseDao<Indent> {

    String BEAN_NAME = "indentDao";
}
