package com.company.api.indent.service;

import java.util.List;

import com.company.dto.indentnode.IndentNodeTreeItem;
import com.company.po.indent.Indent;
import com.company.po.indent.IndentNode;
import com.company.api.fw.service.StringIdBaseService;

public interface IndentNodeService extends StringIdBaseService<IndentNode> {

    String BEAN_NAME = "indentNodeService";
    
    String NODE_SELFCHECK = "SELFCHECK";
    
    String MEASURE = "MEASURE";//测量节点
    
    List<IndentNodeTreeItem> findTreeItems(String id);
    
    List<IndentNodeTreeItem> treeItems(String id);

    void nodeFinished(IndentNode indentNode);

    void deleteByIndentId(String id);

    void nextNodeStar(IndentNode indentNode);

    void inviteEvaluation(String indentNodeId, String id);
    
    IndentNode findSpecifiedNodeByIndentId(String indentId, String nodeCode);
    
    void saveIndentNodes(Indent indent);

    void nodeCentre(IndentNode indentNodeFind);
}