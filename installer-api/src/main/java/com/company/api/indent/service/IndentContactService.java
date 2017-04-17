package com.company.api.indent.service;

import com.company.po.indent.IndentContact;
import com.company.api.fw.service.StringIdBaseService;

public interface IndentContactService extends StringIdBaseService<IndentContact> {
    
    String BEAN_NAME = "indentContactService";

    void deleteByIndentId(String id);
    
    IndentContact findByIndentId(String indentId);

}