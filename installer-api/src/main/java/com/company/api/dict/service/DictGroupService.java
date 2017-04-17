package com.company.api.dict.service;

import com.company.po.dict.DictGroup;
import com.company.api.fw.service.BaseService;

public interface DictGroupService extends BaseService<DictGroup, String> {
    
    String BEAN_NAME = "dictGroupService";
}