package com.company.api.indent.service;

import com.company.po.indent.Indent;
import com.company.po.indent.IndentContent;
import com.company.api.fw.service.StringIdBaseService;

public interface IndentContentService extends StringIdBaseService<IndentContent> {
    
    String BEAN_NAME = "indentContentService";

    void deleteByIndentId(String id);

    Indent saveIndentContent(Indent indent);

}