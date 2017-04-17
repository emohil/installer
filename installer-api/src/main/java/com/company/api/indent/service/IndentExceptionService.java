package com.company.api.indent.service;

import java.util.List;

import com.company.po.indent.IndentException;
import com.company.api.fw.service.StringIdBaseService;

public interface IndentExceptionService extends StringIdBaseService<IndentException> {
    
    String BEAN_NAME = "indentExceptionService";

    void scanException(String id);

    void updateException(IndentException data);

    void saveException(IndentException indentException);

    List<IndentException> findExceptions(IndentException indentException);
    
}