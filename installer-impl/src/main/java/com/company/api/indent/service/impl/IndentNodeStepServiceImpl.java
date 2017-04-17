package com.company.api.indent.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.service.SysDictService;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.indent.service.EnumNodeStepStatus;
import com.company.api.indent.service.IndentNodeStepService;
import com.company.dto.indentnode.IndentNodeTreeItem;
import com.company.po.indent.IndentNodeStep;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.indent.dao.IndentNodeStepDao;
import com.company.util.New;

@Service(IndentNodeStepService.BEAN_NAME)
public class IndentNodeStepServiceImpl extends StringIdBaseServiceImpl<IndentNodeStep>//
        implements IndentNodeStepService {

    @Autowired
    private SqlDao sqlDao;

    @Resource(name = IndentNodeStepService.BEAN_NAME)
    private IndentNodeStepService self;
    
    @Autowired
    private SysDictService sysDictService;

    @Autowired
    public void setBaseDao(IndentNodeStepDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public List<IndentNodeTreeItem> findTreeItems() {

        String sql = new StringBuilder()//
                .append("select ID, NAME1") //
                .append(", INDENT_NODE_ID as PID") //
                .append(", STEP_TYPE") //
                .append(", NODE_STEP_STATUS as stepStatus") //
                .append(" from ZL_INDENT_NODE_STEP") //
                .append(" order by ORDERS") //
                .toString();
        
        List<IndentNodeTreeItem> indentNodes = sqlDao.listAll(sql, IndentNodeTreeItem.class);
        for (IndentNodeTreeItem indentNodeTreeItem : indentNodes) {
            String stepStatusDisp = sysDictService.text(EnumCodes.INDENT_NODE_STEP_STATUS, indentNodeTreeItem.getStepStatus());
            indentNodeTreeItem.setStepStatusDisp(stepStatusDisp);
        }
        return indentNodes;
    }

    @Autowired
    public Map<String, List<IndentNodeTreeItem>> scnode2TreeItems() {

        Map<String, List<IndentNodeTreeItem>> ret = New.hashMap();
        for (IndentNodeTreeItem item : self.findTreeItems()) {
            List<IndentNodeTreeItem> items = ret.get(item.getPid());
            if (items == null) {
                items = New.list();
                ret.put(item.getPid(), items);
            }
            items.add(item);
        }

        return ret;
    }

    @Override
    public void nodeStepFinished(String indentNodeStepId) {
        
        String sql = "update ZL_INDENT_NODE_STEP set NODE_STEP_STATUS = ? where ID = ?";
        sqlDao.execUpdate(sql, EnumNodeStepStatus.FINISH.name(), indentNodeStepId);
        
    }
    
    @Override
    public IndentNodeStep findByNodeIdAndType(String indentNodeId, String stepType) {
        String sql = "select * from ZL_INDENT_NODE_STEP where INDENT_NODE_ID=? and STEP_TYPE=?";
        return sqlDao.unique(sql, IndentNodeStep.class, indentNodeId, stepType);
    }

    @Override
    public int finishedCounts(String indentNodeId) {
        String sql = "SELECT COUNT(*) from ZL_INDENT_NODE_STEP WHERE NODE_STEP_STATUS = ? AND INDENT_NODE_ID = ?";
        return sqlDao.count(sql, EnumNodeStepStatus.FINISH.name(), indentNodeId);
    }
    
}