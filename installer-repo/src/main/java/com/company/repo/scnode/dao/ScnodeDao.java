package com.company.repo.scnode.dao;

import com.company.po.scnode.Scnode;
import com.company.repo.fw.dao.BaseDao;

public interface ScnodeDao extends BaseDao<Scnode, String> {

    String BEAN_NAME = "scnodeDao";
}
