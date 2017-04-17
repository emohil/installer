package com.company.api.indent.service;

import com.company.po.indent.IndentFreight;
import com.company.api.fw.service.StringIdBaseService;

public interface IndentFreightService extends StringIdBaseService<IndentFreight> {
    
    String BEAN_NAME = "indentFreightService";

    void deleteByIndentId(String id);

}