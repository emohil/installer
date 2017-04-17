package com.company.api.indent.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.company.api.fs.service.FileManagerService;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.indent.service.EnumExecuteStatus;
import com.company.api.indent.service.EnumNodeStepStatus;
import com.company.api.indent.service.IndentNodeService;
import com.company.api.indent.service.IndentNodeStepItemService;
import com.company.api.indent.service.IndentNodeStepService;
import com.company.api.indent.service.IndentService;
import com.company.api.scnode.service.EnumScnodeStepType;
import com.company.dto.indentnode.IndentNodeTreeItem;
import com.company.po.fs.UnFileIndex;
import com.company.po.indent.IndentNode;
import com.company.po.indent.IndentNodeStep;
import com.company.po.indent.IndentNodeStepItem;
import com.company.dto.Filter;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.indent.dao.IndentNodeStepItemDao;
import com.company.util.Dto;
import com.company.util.New;

@Service(IndentNodeStepItemService.BEAN_NAME)
public class IndentNodeStepItemServiceImpl extends StringIdBaseServiceImpl<IndentNodeStepItem>//
        implements IndentNodeStepItemService {
    
    @Autowired
    private SqlDao sqlDao;
    
    @Autowired
    private IndentService indentService;
    
    @Autowired
    private IndentNodeStepService indentNodeStepService;
    
    @Autowired
    private IndentNodeService indentNodeService;
    
    @Autowired
    private FileManagerService fileManagerService;

    @Autowired
    public void setBaseDao(IndentNodeStepItemDao dao) {
        super.setBaseDao(dao);
    }
    
    
    @Override
    public List<IndentNodeTreeItem> findTreeItems() {
        
        String sql = new StringBuilder()//
                .append("select ID, DESC1 as NAME1") //
                .append(", INDENT_NODE_STEP_ID as PID") //
                .append(" from ZL_INDENT_NODE_STEP_ITEM") //
                .append(" order by ORDERS") //
                .toString();

        return sqlDao.listAll(sql, IndentNodeTreeItem.class);
    }

    @Autowired
    public Map<String, List<IndentNodeTreeItem>> step2TreeItems() {

        Map<String, List<IndentNodeTreeItem>> ret = New.hashMap();
        for (IndentNodeTreeItem item : findTreeItems()) {
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
    public int finishedItemCounts(String indentNodeId) {
        String sql = "SELECT COUNT(*) FROM ZL_INDENT_NODE_STEP_ITEM WHERE INDENT_NODE_STEP_ID = ? AND `STATUS` = ?";
        int i = sqlDao.count(sql, indentNodeId, EnumNodeStepStatus.FINISH.name());
        return i;
    }


    @Override
    public void finishTipText(String id) {
        String sql = "update ZL_INDENT_NODE_STEP_ITEM set STATUS = ? where INDENT_NODE_STEP_ID = ?";
        sqlDao.execUpdate(sql, EnumNodeStepStatus.FINISH.name(), id);
        indentNodeStepService.nodeStepFinished(id);
    }
    
    @Override
    public IndentNodeStepItem findByNodeStepId(String indentNodeStepId) {
        String sql = "select * from ZL_INDENT_NODE_STEP_ITEM where INDENT_NODE_STEP_ID=?";
        return sqlDao.unique(sql, IndentNodeStepItem.class, indentNodeStepId);
    }


    @Override
    public Dto dealItem(Map<String, MultipartFile> files, String indentNodeStepItemId) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, true);
        IndentNodeStepItem indentNodeStepItemFind = find(indentNodeStepItemId);
        IndentNode indentNodeFind = indentNodeService.find(indentNodeStepItemFind.getIndentNodeId());
        
        //第一次操作节点  把节点状态设为   center    设置订单的  progStatus
        if(EnumExecuteStatus.BEFORE.name().equals(indentNodeFind.getNodeExecuteStatus())) {
            indentNodeFind.setNodeExecuteStatus(EnumExecuteStatus.CENTRE.name());
            indentNodeService.nodeCentre(indentNodeFind);
            indentService.firstOperItem(indentNodeFind);
        }
        List<IndentNodeStepItem> itemList = findList(Filter.eq("indentNodeStepId", indentNodeStepItemFind.getIndentNodeStepId()));
        if (files != null) {
            String itemId = "";
            Iterator<MultipartFile> iterator = files.values().iterator();
            while (iterator.hasNext()) {
                MultipartFile multipartFile = (MultipartFile) iterator.next();
                itemId = multipartFile.getName().substring(0, 32);
                UnFileIndex unFileIndex = new UnFileIndex(multipartFile, multipartFile.getName().substring(0, 32), "INDENT", "ITEM");
                fileManagerService.save(unFileIndex);
            }
            IndentNodeStepItem indentNodeStepItem = find(itemId);
            if (EnumScnodeStepType.UPLOAD.name().equals(indentNodeStepItem.getItemType()) && files.size() < 1) {
                ret.put(TAG_SUCCESS, false);
                ret.put(TAG_MSG, "上传失败!");
                return ret;
            }
            indentNodeStepItem.setStatus(EnumNodeStepStatus.FINISH.name());
            itemFinished(indentNodeStepItem.getId());
        } else {
            indentNodeStepItemFind.setStatus(EnumNodeStepStatus.FINISH.name());
            itemFinished(indentNodeStepItemFind.getId());
        }
        
        //节点里的文字描述   提交的时候  都成为  finish
        List<IndentNodeStep> indentNodeStepList = indentNodeStepService.findList(Filter.eq("indentNodeId", indentNodeFind.getId()));
        for (IndentNodeStep indentNodeStep : indentNodeStepList) {
            if (EnumScnodeStepType.TIPTEXT.name().equals(indentNodeStep.getStepType())) {
                finishTipText(indentNodeStep.getId());
            }
        }
        //设置节点轨迹完成
        int itemCount = finishedCounts(indentNodeStepItemFind.getIndentNodeStepId());
        if (itemCount == itemList.size()) {
            indentNodeStepService.nodeStepFinished(indentNodeStepItemFind.getIndentNodeStepId());
        }
        
        //判断节点完成
        List<IndentNodeStep> finishedStepList = indentNodeStepService.findList(Filter.eq("indentNodeId", indentNodeStepItemFind.getIndentNodeId()));
        int stepCount = indentNodeStepService.finishedCounts(indentNodeStepItemFind.getIndentNodeId());
        Dto data = new Dto();
        data.put("bottonViem", false);
        if (stepCount == finishedStepList.size()) {
            data.put("bottonViem", true);
            ret.put(TAG_DATA, data);
        }
        return ret;
    }


    private int finishedCounts(String indentNodeStepId) {
        String sql = "SELECT COUNT(*) from ZL_INDENT_NODE_STEP_ITEM WHERE STATUS = ? AND INDENT_NODE_STEP_ID = ?";
        return sqlDao.count(sql, EnumNodeStepStatus.FINISH.name(), indentNodeStepId);
    }


    @Override
    public void itemFinished(String indentNodeStepItemId) {
        
        String sql = "update ZL_INDENT_NODE_STEP_ITEM set STATUS = ? where ID = ?";
        sqlDao.execUpdate(sql, EnumNodeStepStatus.FINISH.name(), indentNodeStepItemId);
    }
    
}