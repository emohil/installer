package com.company.api.dict.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.dict.service.DictGroupService;
import com.company.api.fw.service.impl.BaseServiceImpl;
import com.company.po.dict.DictGroup;
import com.company.repo.dict.dao.DictGroupDao;

@Service(DictGroupService.BEAN_NAME)
public class DictGroupServiceImpl extends BaseServiceImpl<DictGroup, String>implements DictGroupService {

    @Autowired
    public void setBaseDao(DictGroupDao dictGroupDao) {
        super.setBaseDao(dictGroupDao);
    }
}