package com.company.api.aparty.service;

import com.company.po.aparty.Aparty;
import com.company.sf.aparty.ApartySf;
import com.company.api.fw.service.PagerService;
import com.company.api.fw.service.PairTypehead;
import com.company.api.fw.service.StringIdBaseService;
import com.company.util.Dto;

public interface ApartyService extends StringIdBaseService<Aparty>, //
        PairTypehead, PagerService<ApartySf, Aparty> {

    String BEAN_NAME = "apartyService";

    @Override
    Aparty find(String id);

    Dto deleteAparty(String id);

    String findMaxCode1();

    String getNextCode1(String maxCode1);

    Aparty findApartyByCode1(String code1);
}