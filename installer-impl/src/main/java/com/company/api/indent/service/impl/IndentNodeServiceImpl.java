package com.company.api.indent.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.service.SysDictService;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.indent.service.EnumExceptionResultStatus;
import com.company.api.indent.service.EnumExecuteStatus;
import com.company.api.indent.service.EnumNodeStepStatus;
import com.company.api.indent.service.IndentExceptionService;
import com.company.api.indent.service.IndentNodeService;
import com.company.api.indent.service.IndentNodeStepItemService;
import com.company.api.indent.service.IndentNodeStepService;
import com.company.api.indent.service.IndentService;
import com.company.api.scnode.service.EnumScnodeStepType;
import com.company.api.scnode.service.ScnodeService;
import com.company.api.scnode.service.ScnodeStepItemService;
import com.company.api.scnode.service.ScnodeStepService;
import com.company.api.sctype.service.SctypeNodeService;
import com.company.dto.indentnode.IndentNodeTreeItem;
import com.company.po.indent.Indent;
import com.company.po.indent.IndentException;
import com.company.po.indent.IndentNode;
import com.company.po.indent.IndentNodeStep;
import com.company.po.indent.IndentNodeStepItem;
import com.company.po.scnode.Scnode;
import com.company.po.scnode.ScnodeStep;
import com.company.po.scnode.ScnodeStepItem;
import com.company.po.sctype.SctypeNode;
import com.company.dto.Filter;
import com.company.dto.Order;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.indent.dao.IndentNodeDao;
import com.company.util.New;

@Service(IndentNodeService.BEAN_NAME)
public class IndentNodeServiceImpl extends StringIdBaseServiceImpl<IndentNode>//
        implements IndentNodeService {

    @Autowired
    private SqlDao sqlDao;

    @Resource(name = IndentNodeService.BEAN_NAME)
    private IndentNodeService self;

    @Autowired
    private IndentService indentService;

    @Autowired
    private IndentNodeStepService indentNodeStepService;

    @Autowired
    private IndentExceptionService ieService;

    @Autowired
    private IndentNodeStepItemService indentNodeStepItemService;
    
    @Autowired
    private ScnodeStepItemService scnodeStepItemService;
    
    @Autowired
    private SctypeNodeService sctypeNodeService;
    
    @Autowired
    private ScnodeStepService scnodeStepService;
    
    @Autowired
    private ScnodeService scnodeService;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    public void setBaseDao(IndentNodeDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public List<IndentNodeTreeItem> findTreeItems(String id) {
        List<IndentNodeTreeItem> indentNodes = //
        sqlDao.listAll(
                "select node.ID, node.CODE1, node.NAME1, node.NODE_EXECUTE_STATUS as stepStatus, excep.RESULT as result from ZL_INDENT_NODE node LEFT JOIN (SELECT * from ZL_INDENT_EXCEP WHERE RESULT = 'SUSPEND') excep on node.ID = excep.INDENT_NODE_ID where node.INDENT_ID=? order by node.ORDERS, excep.ACCEPTED_DATE DESC",
                IndentNodeTreeItem.class, id);
        for (IndentNodeTreeItem indentNodeTreeItem : indentNodes) {
            String stepStatusDisp = sysDictService.text(EnumCodes.INDENT_EXECUTE_STATUS,
                    indentNodeTreeItem.getStepStatus());
            indentNodeTreeItem.setStepStatusDisp(stepStatusDisp);
        }

        return indentNodes;
    }

    @Override
    public List<IndentNodeTreeItem> treeItems(String id) {

        List<IndentNodeTreeItem> nodeList = self.findTreeItems(id);

        for (IndentNodeTreeItem indentNodeTreeItem : nodeList) {
            List<IndentException> exList = ieService
                    .findList(Arrays.asList(Filter.eq("indentNodeId", indentNodeTreeItem.getId()),
                            Filter.ne("executeStatus", EnumExecuteStatus.AFTER.name()),//ne  !=
                            Filter.ne("result", EnumExceptionResultStatus.CONTINUE.name()),
                            Filter.ne("result", EnumExceptionResultStatus.OVER.name())));
            indentNodeTreeItem.setiEList(exList);
        }

        Map<String, List<IndentNodeTreeItem>> scnode2Steps = indentNodeStepService.scnode2TreeItems();

        Map<String, List<IndentNodeTreeItem>> step2Items = indentNodeStepItemService.step2TreeItems();

        for (IndentNodeTreeItem node : nodeList) {

            List<IndentNodeTreeItem> stepList = scnode2Steps.get(node.getId());

            if (stepList == null) {
                continue;
            }
            node.setChildren(stepList);

            for (IndentNodeTreeItem step : stepList) {
                List<IndentNodeTreeItem> itemList = step2Items.get(step.getId());
                step.setChildren(itemList);
            }
        }

        return nodeList;
    }

    @Override
    public void nodeFinished(IndentNode indentNode) {
        String sql = "update ZL_INDENT_NODE set NODE_EXECUTE_STATUS = ? where ID = ?";
        sqlDao.execUpdate(sql, EnumExecuteStatus.AFTER.name(), indentNode.getId());
        this.nextNodeStar(indentNode);
    }

    @Override
    public void deleteByIndentId(String id) {

        List<IndentNode> indentNodeList = findList(Filter.eq("indentId", id));
        List<IndentNodeStep> indentNodeStepList = New.list();
        List<IndentNodeStepItem> indentNodeStepItemList = New.list();

        for (IndentNode indentNode : indentNodeList) {
            indentNodeStepList = indentNodeStepService.findList(Filter.eq("indentNodeId", indentNode.getId()));
            indentNodeStepItemList = indentNodeStepItemService.findList(Filter.eq("indentNodeId", indentNode.getId()));
        }
        for (IndentNodeStepItem indentNodeStepItem : indentNodeStepItemList) {
            indentNodeStepItemService.delete(indentNodeStepItem.getId());
        }

        for (IndentNodeStep indentNodeStep : indentNodeStepList) {
            indentNodeStepService.delete(indentNodeStep.getId());
        }

        String sql = "delete from ZL_INDENT_NODE where INDENT_ID = ?";
        sqlDao.execUpdate(sql, id);
    }

    @Override
    public void nextNodeStar(IndentNode indentNode) {
        List<IndentNode> list = findList(Filter.eq("indentId", indentNode.getIndentId()), Order.asc("orders"));
        int nodeIndex = list.indexOf(indentNode);
        int nextIndex = nodeIndex + 1;
        if (nextIndex < list.size()) {

            IndentNode nextNode = list.get(nextIndex);
            nextNode.setNodeExecuteStatus(EnumExecuteStatus.CENTRE.name());
            String sql = "update ZL_INDENT_NODE set NODE_EXECUTE_STATUS = ? WHERE ID = ?";
            sqlDao.execUpdate(sql, EnumExecuteStatus.CENTRE.name(), nextNode.getId());
            indentService.nodeProgress(indentNode);
        }

    }

    @Override
    public void inviteEvaluation(String indentNodeId, String id) {

        String sqlItem = "update ZL_INDENT_NODE_STEP_ITEM set STATUS = ? where INDENT_NODE_ID = ?";
        sqlDao.execUpdate(sqlItem, EnumNodeStepStatus.FINISH.name(), indentNodeId);

        String sqlStep = "update ZL_INDENT_NODE_STEP set NODE_STEP_STATUS = ? where INDENT_NODE_ID = ?";
        sqlDao.execUpdate(sqlStep, EnumNodeStepStatus.FINISH.name(), indentNodeId);

        String sqlNode = "update ZL_INDENT_NODE set NODE_EXECUTE_STATUS = ? where ID = ?";
        sqlDao.execUpdate(sqlNode, EnumExecuteStatus.AFTER.name(), indentNodeId);
        
        String sqlIndent = "update ZL_INDENT set FINISH_DATE =?, PROG_STATUS = ? where ID = ?";
        sqlDao.execUpdate(sqlIndent, new Date(), EnumScnodeStepType.INVITEEVAL.name(), id);

    }

    @Override
    public IndentNode findSpecifiedNodeByIndentId(String indentId, String nodeCode) {
        String sql = new StringBuilder()//
                .append("select ID, CODE1, NAME1, NODE_EXECUTE_STATUS")//
                .append(" from ZL_INDENT_NODE") //
                .append(" where INDENT_ID=?")//
                .append(" and CODE1=?") //
                .toString();
        return sqlDao.unique(sql, IndentNode.class, new Object[] { indentId, nodeCode });
    }
    
    @Override
    public void saveIndentNodes(Indent indent) {
        List<SctypeNode> sctypeNodeList = sctypeNodeService.findList(Filter.eq("sctypeId", indent.getServeType()));
        for (SctypeNode sctypeNode : sctypeNodeList) {
            //Scnode scnode = scnodeService.findOne(Filter.eq("id", sctypeNode.getScnodeId()), Filter.eq("scnodeIndentType", indent.getSource()));
            Scnode scnode = scnodeService.find(sctypeNode.getScnodeId());
            IndentNode indentNode = new IndentNode();
            indentNode.setIndentId(indent.getId());
            indentNode.setName1(scnode.getName1());
            indentNode.setCode1(scnode.getCode1());
            indentNode.setOrders(scnode.getOrders());
            indentNode.setNodeExecuteStatus(EnumExecuteStatus.BEFORE.name());
            save(indentNode);
            List<ScnodeStep> scnodeStepList = scnodeStepService.findList(Filter.eq("scnodeId", scnode.getId()));
            
            for (ScnodeStep scnodeStep : scnodeStepList) {
                String sorts = indent.getIndentSortDto().values().toString();
                IndentNodeStep indentNodeStep = new IndentNodeStep();
                if (scnodeStep.getStepType().equals(EnumScnodeStepType.UPLOAD.name())) {
                    String photoName;
                    if (scnodeStep.getName1().length() > 3) {
                        photoName = scnodeStep.getName1().replace("拍照", "照片");
                        indentNodeStep.setPhotoName(photoName);
                    }
                    if (scnodeStep.getName1().length() == 2) {
                        photoName = "测量";
                        indentNodeStep.setPhotoName(photoName);
                    }
                }
                indentNodeStep.setIndentNodeId(indentNode.getId());
                indentNodeStep.setName1(scnodeStep.getName1());
                indentNodeStep.setStepType(scnodeStep.getStepType());
                indentNodeStep.setOrders(scnodeStep.getOrders());
                indentNodeStep.setSortId(scnodeStep.getSortId());
                indentNodeStep.setNodeStepStatus(EnumNodeStepStatus.UNFINISHED.name());
                if ((!scnodeStep.getScnodeId().equals("SELFCHECK") || sorts.contains(scnodeStep.getSortId()))) {
                    indentNodeStepService.save(indentNodeStep);
                    List<ScnodeStepItem> ScnodeStepItemList = //
                    scnodeStepItemService.findList(Filter.eq("scnodeStepId", scnodeStep.getId()));
                    for (ScnodeStepItem scnodeStepItem : ScnodeStepItemList) {
                        IndentNodeStepItem indentNodeStepItem = new IndentNodeStepItem();
                        indentNodeStepItem.setDesc1(scnodeStepItem.getDesc1());
                        indentNodeStepItem.setOrders(scnodeStepItem.getOrders());
                        indentNodeStepItem.setIndentNodeId(indentNodeStep.getIndentNodeId());
                        indentNodeStepItem.setItemType(scnodeStepItem.getItemType());
                        indentNodeStepItem.setNodeStepItemId(scnodeStepItem.getId());
                        indentNodeStepItem.setStatus(EnumNodeStepStatus.UNFINISHED.name());
                        indentNodeStepItem.setIndentNodeStepId(indentNodeStep.getId());
                        indentNodeStepItem.setIsSketch(scnodeStepItem.getIsSketch());
                        indentNodeStepItem.setMinPhoto(scnodeStepItem.getMinPhoto());
                        indentNodeStepItemService.save(indentNodeStepItem);
                    }
                }
            }
        }
    }

    @Override
    public void nodeCentre(IndentNode indentNodeFind) {
        
        String sql = "update ZL_INDENT_NODE set NODE_EXECUTE_STATUS = ? where ID = ?";
        sqlDao.execUpdate(sql, EnumExecuteStatus.CENTRE.name(), indentNodeFind.getId());
    }

}