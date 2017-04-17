package com.company.api.scnode.service;

import java.util.List;

import com.company.dto.scnode.ScnodeTreeItem;
import com.company.po.scnode.Scnode;
import com.company.api.fw.service.StringIdBaseService;

public interface ScnodeService extends StringIdBaseService<Scnode> {

    String BEAN_NAME = "scnodeService";
    
    List<ScnodeTreeItem> findTreeItems();
    
    List<ScnodeTreeItem> treeItems();
}