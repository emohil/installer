package com.company.repo.dict.dao;

import java.util.List;

import com.company.po.dict.Dict;
import com.company.repo.fw.dao.BaseDao;

public interface DictDao extends BaseDao<Dict, String> {
    
    String BEAN_NAME = "dictDao";

    void deleteByGroupCode(String groupCode);

    List<Dict> findByGroupCode(String groupCode);


}