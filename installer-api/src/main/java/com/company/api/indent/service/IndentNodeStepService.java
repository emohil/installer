package com.company.api.indent.service;

import java.util.List;
import java.util.Map;

import com.company.dto.indentnode.IndentNodeTreeItem;
import com.company.po.indent.IndentNodeStep;
import com.company.api.fw.service.StringIdBaseService;

public interface IndentNodeStepService extends StringIdBaseService<IndentNodeStep> {

    String BEAN_NAME = "indentNodeStepService";
    
    /**
     * 获取所有的服务节点步骤
     * @return
     */
    List<IndentNodeTreeItem> findTreeItems();
    
    /**
     * 按服务节点分组 服务步骤项
     * @return
     */
    Map<String, List<IndentNodeTreeItem>> scnode2TreeItems();

    void nodeStepFinished(String indentNodeStepId);
    
    IndentNodeStep findByNodeIdAndType(String indentNodeId, String stepType);

    int finishedCounts(String indentNodeId);
    
}