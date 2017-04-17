package com.company.api.sctype.service;

import java.util.List;

import com.company.dto.sctype.SctypeTreeItem;
import com.company.po.sctype.Sctype;
import com.company.api.fw.service.StringIdBaseService;

public interface SctypeService extends StringIdBaseService<Sctype> {

    String BEAN_NAME = "serveTypeService";

    /**
     * 获取所有的服务类型项
     * @return
     */
    List<SctypeTreeItem> findTreeItems();
    
    List<SctypeTreeItem> treeItems();
}