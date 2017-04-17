package com.company.api.dict.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.dict.service.DictService;
import com.company.api.fw.service.impl.BaseServiceImpl;
import com.company.po.dict.Dict;
import com.company.repo.dict.dao.DictDao;

@Service(DictService.BEAN_NAME)
public class DictServiceImpl extends BaseServiceImpl<Dict, String>implements DictService {

    @Autowired
    private DictDao dictDao;

    @Autowired
    public void setBaseDao(DictDao dictDao) {
        super.setBaseDao(dictDao);
    }

    public void deleteByGroupCode(String groupCode) {
        dictDao.deleteByGroupCode(groupCode);
    }

    public List<Dict> findByGroupCode(String groupCode) {
        return dictDao.findByGroupCode(groupCode);
    }
}