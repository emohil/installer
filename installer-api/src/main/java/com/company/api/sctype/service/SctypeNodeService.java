package com.company.api.sctype.service;

import java.util.List;

import com.company.po.sctype.Sctype;
import com.company.po.sctype.SctypeNode;
import com.company.api.fw.service.StringIdBaseService;

public interface SctypeNodeService extends StringIdBaseService<SctypeNode> {
    
    String BEAN_NAME = "serveNodeService";
    
    List<SctypeNode> findBySctypeId(String sctypeId);

    Sctype loadData(String sctypeId);
    
    void saveNodes(Sctype sctype);
}