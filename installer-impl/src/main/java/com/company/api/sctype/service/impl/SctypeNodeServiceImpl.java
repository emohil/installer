package com.company.api.sctype.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.sctype.service.SctypeNodeService;
import com.company.api.sctype.service.SctypeService;
import com.company.po.sctype.Sctype;
import com.company.po.sctype.SctypeNode;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.sctype.dao.SctypeNodeDao;
import com.company.util.BooleanUtil;
import com.company.util.New;

@Service(SctypeNodeService.BEAN_NAME)
public class SctypeNodeServiceImpl extends StringIdBaseServiceImpl<SctypeNode>//
        implements SctypeNodeService {

    private static final String ID = "ID";

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    private SctypeService sctypeService;

    @Autowired
    public void setBaseDao(SctypeNodeDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public List<SctypeNode> findBySctypeId(String sctypeId) {

        String sql = "select SCNODE_ID from ZL_SCTYPE_NODE where SCTYPE_ID=?";

        return sqlDao.listAll(sql, SctypeNode.class, new Object[] { sctypeId });
    }

    @Override
    public void saveNodes(Sctype sctype) {

        String sctypeId = sctype.getId();

        if (sctype.getNodes() == null || sctype.getNodes().size() == 0) {
            sqlDao.execUpdate("delete from ZL_SCTYPE_NODE where SCTYPE_ID=?", new Object[] { sctypeId });
            return;
        }

        Map<String, Boolean> nodes = New.linkedMap();
        for (Entry<String, Boolean> e : sctype.getNodes().entrySet()) {
            nodes.put(e.getKey().substring(ID.length()), e.getValue());
        }

        String sql = "select * from ZL_SCTYPE_NODE where SCTYPE_ID=?";
        List<SctypeNode> existing = sqlDao.listAll(sql, SctypeNode.class, new Object[] { sctypeId });

        // 先移除取消勾选的项
        for (SctypeNode sctypeNode : existing) {
            if (!BooleanUtil.getBoolean(nodes.get(sctypeNode.getScnodeId()))) {
                sqlDao.execUpdate("delete from ZL_SCTYPE_NODE where ID=?", new Object[] { sctypeNode.getId() });
            }
            nodes.remove(sctypeNode.getScnodeId());
        }
        // 保存新勾选的项
        for (String nodeId : nodes.keySet()) {
            SctypeNode sctypeNode = new SctypeNode();
            sctypeNode.setSctypeId(sctypeId);
            sctypeNode.setScnodeId(nodeId);
            super.save(sctypeNode);
        }
    }

    @Override
    public Sctype loadData(String sctypeId) {
        Sctype sctype = sctypeService.find(sctypeId);

        List<SctypeNode> sctypeNodes = findBySctypeId(sctypeId);

        for (SctypeNode node : sctypeNodes) {
            sctype.getNodes().put(ID + node.getScnodeId(), true);
        }

        return sctype;
    }
}