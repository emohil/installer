package com.company.api.indent.service;

import com.company.po.indent.IndentEvaluate;
import com.company.api.fw.service.StringIdBaseService;

public interface IndentEvaluateService extends StringIdBaseService<IndentEvaluate> {
    
    String BEAN_NAME = "indentEvaluateService";
    
    IndentEvaluate findByIndentId(String indentId);

    void saveEvalute(IndentEvaluate indentEvaluate);


}