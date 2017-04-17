package com.company.api.dict.service;

import java.util.List;

import com.company.po.dict.Dict;
import com.company.api.fw.service.BaseService;

public interface DictService extends BaseService<Dict, String> {
    
    String BEAN_NAME = "dictService";

    void deleteByGroupCode(String groupCode);

    List<Dict> findByGroupCode(String groupCode);

}