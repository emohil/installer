package com.company.api.indent.service;

import com.company.po.indent.IndentSort;
import com.company.api.fw.service.StringIdBaseService;

public interface IndentSctypeSortService extends StringIdBaseService<IndentSort> {
    
    String BEAN_NAME = "indentSctypeSortService";

    void deleteByIndentId(String id);

}