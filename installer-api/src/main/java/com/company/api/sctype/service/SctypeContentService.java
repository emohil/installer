package com.company.api.sctype.service;

import java.util.List;
import java.util.Map;

import com.company.dto.sctype.SctypeTreeItem;
import com.company.po.sctype.SctypeContent;
import com.company.api.fw.service.StringIdBaseService;

public interface SctypeContentService extends StringIdBaseService<SctypeContent> {

    String BEAN_NAME = "serveContentService";

    /**
     * 根据ID加载数据
     * 
     * @param id
     * @return
     */
    SctypeContent load(String id);

    /**
     * 获取所有的服务内容项
     * 
     * @return
     */
    List<SctypeTreeItem> findTreeItems();

    /**
     * 按类别分组 服务内容项
     * 
     * @return
     */
    Map<String, List<SctypeTreeItem>> sort2TreeItems();
}