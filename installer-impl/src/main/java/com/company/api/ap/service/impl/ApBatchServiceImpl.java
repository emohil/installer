package com.company.api.ap.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.ap.service.ApBatchService;
import com.company.api.ap.service.ApIndentService;
import com.company.api.ap.service.ApManagerService;
import com.company.api.ap.service.ApWorkerService;
import com.company.api.dict.service.EnumBatchStatus;
import com.company.api.dict.service.EnumYesno;
import com.company.api.fw.service.impl.PagerBaseServiceImpl;
import com.company.api.fw.service.impl.query.CompareOps;
import com.company.api.fw.service.impl.query.QueryInfo;
import com.company.api.fw.service.impl.query.QueryInfoBuilder;
import com.company.api.indent.service.EnumExecuteStatus;
import com.company.api.indent.service.EnumIndentStatus;
import com.company.api.mgr.service.ManagerTradeService;
import com.company.api.wk.service.WorkerTradeService;
import com.company.po.ap.ApBatch;
import com.company.po.ap.ApIndent;
import com.company.po.ap.ApManager;
import com.company.po.ap.ApWorker;
import com.company.po.indent.Indent;
import com.company.sf.ap.ApBatchSf;
import com.company.repo.ap.dao.ApBatchDao;
import com.company.repo.fw.dao.SqlDao;
import com.company.util.DateUtil;
import com.company.util.Dto;
import com.company.util.New;

@Service(ApBatchService.BEAN_NAME)
public class ApBatchServiceImpl extends PagerBaseServiceImpl<ApBatchSf, ApBatch>//
        implements ApBatchService {

    @Autowired
    private ApManagerService apManagerService;

    @Autowired
    private ApWorkerService apWorkerService;

    @Autowired
    private ApIndentService apIndentService;

    @Autowired
    private ManagerTradeService managerTradeService;

    @Autowired
    private WorkerTradeService workerTradeService;

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    public void setBaseDao(ApBatchDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public QueryInfoBuilder prepareQuery(ApBatchSf sf) {
        if (sf == null) {
            sf = new ApBatchSf();
        }
        StringBuilder sql = new StringBuilder()//
                .append("select * from ZL_AP_BATCH")//
                .append(" WHERE 1=1");

        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql.toString())//
                .and("DESC1", CompareOps.CONTAINS, sf.getDesc1())//
                .and("BATCH_DATE", CompareOps.GE, sf.getBatchDateBegin())//
                .and("BATCH_DATE", CompareOps.LE, sf.getBatchDateEnd())//
                ;

        return builder;
    }

    @Override
    public Dto doPreview(ApBatch data) {

        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);

        List<Indent> indentList = this.findIndentList(data);

        if (indentList.size() == 0) {
            ret.put(TAG_MSG, "当前没有符合条件的订单可结算！");
            return ret;
        }

        ret.put(TAG_SUCCESS, true);
        ret.put(TAG_LIST, indentList);
        return ret;
    }

    @Override
    public Dto doSave(ApBatch data) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);

        List<String> indentIdList = data.getIndentIdList();
        if (indentIdList == null || indentIdList.size() == 0) {
            ret.put(TAG_MSG, "没有订单需要结算");
            return ret;
        }

        // 第一步， 保存批次表
        data.setIndents(0);
        data.setManagers(0);
        data.setWorkers(0);
        data.setIndentAmt(BigDecimal.ZERO);
        data.setManagerAmt(BigDecimal.ZERO);
        data.setWorkerAmt(BigDecimal.ZERO);
        data.setProfitAmt(BigDecimal.ZERO);
        data.setBatchStatus(EnumBatchStatus.UNTREATED.name());
        this.save(data);

        String apBatchId = data.getId();

        List<Indent> indentList = findIndentListByIds(indentIdList);

        Map<String, Set<String>> mgr2WkList = this.mgr2WkSet(indentList);
        Map<String, List<Indent>> wk2IndentList = this.wk2IndentList(indentList);

        int managerNo = 0, workerNo = 0, indentNo = 0;
        for (Entry<String, Set<String>> eMgr : mgr2WkList.entrySet()) {
            String managerId = eMgr.getKey();

            ApManager apManager = new ApManager();
            apManager.setApBatchId(apBatchId);
            apManager.setManagerId(managerId);
            apManager.setWorkers(0);
            apManager.setIndents(0);
            apManager.setManagerAmt(BigDecimal.ZERO);
            apManager.setLineNo(++managerNo);

            for (String workerId : eMgr.getValue()) {

                ApWorker apWorker = new ApWorker();
                apWorker.setApBatchId(apBatchId);
                apWorker.setManagerId(managerId);
                apWorker.setWorkerId(workerId);
                apWorker.setIndents(0);
                apWorker.setWorkerAmt(BigDecimal.ZERO);
                apWorker.setLineNo(++workerNo);

                List<Indent> wkIndentList = wk2IndentList.get(workerId);

                for (Indent indent : wkIndentList) {
                    String indentId = indent.getId();

                    ApIndent apIndent = new ApIndent();
                    apIndent.setApBatchId(apBatchId);
                    apIndent.setIndentId(indentId);
                    apIndent.setIndentCode(indent.getCode1());
                    apIndent.setManagerId(managerId);
                    apIndent.setWorkerId(workerId);
                    apIndent.setLineNo(++indentNo);

                    apIndent.setIndentAmt(indent.getSum());
                    apIndent.setManagerAmt(indent.getManagerFee());
                    apIndent.setWorkerAmt(indent.getWorkerFee());
                    apIndent.setProfitAmt(indent.getProfit());

                    apIndentService.save(apIndent);

                    // apWorker
                    apWorker.setIndents(apWorker.getIndents() + 1);
                    apWorker.setWorkerAmt(apWorker.getWorkerAmt().add(apIndent.getWorkerAmt()));
                    // apManager
                    apManager.setManagerAmt(apManager.getManagerAmt().add(apIndent.getManagerAmt()));
                    // apBatch
                    data.setIndentAmt(data.getIndentAmt().add(apIndent.getIndentAmt()));
                    data.setProfitAmt(data.getProfitAmt().add(apIndent.getProfitAmt()));
                }
                apWorkerService.save(apWorker);
                // apManager
                apManager.setWorkers(apManager.getWorkers() + 1);
                apManager.setIndents(apManager.getIndents() + apWorker.getIndents());
                // apBatch
                data.setWorkerAmt(data.getWorkerAmt().add(apWorker.getWorkerAmt()));
            }

            apManagerService.save(apManager);

            data.setIndents(data.getIndents() + apManager.getIndents());
            data.setManagers(data.getManagers() + 1);
            data.setWorkers(data.getWorkers() + apManager.getWorkers());
            data.setManagerAmt(data.getManagerAmt().add(apManager.getManagerAmt()));
        }

        ret.put(TAG_MSG, "批次创建成功！");
        ret.put(TAG_SUCCESS, true);
        return ret;
    }

    @Override
    public Dto doDelete(String id) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);

        ApBatch data = find(id);
        if (data == null) {
            ret.put(TAG_MSG, "提供的批次ID无效！");
            return ret;
        }
        if (!EnumBatchStatus.UNTREATED.name().equals(data.getBatchStatus())) {
            ret.put(TAG_MSG, "批次的当前状态不允许删除！");
            return ret;
        }

        data.setBatchStatus(EnumBatchStatus.DELETED.name());

        apManagerService.deleteByBatchId(id);
        apWorkerService.deleteByBatchId(id);
        apIndentService.deleteByBatchId(id);

        ret.put(TAG_MSG, "删除成功！");
        ret.put(TAG_SUCCESS, true);
        return ret;
    }

    @Override
    public Dto doPost(String id) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);

        ApBatch data = find(id);
        if (data == null) {
            ret.put(TAG_MSG, "提供的批次ID无效！");
            return ret;
        }
        if (!EnumBatchStatus.UNTREATED.name().equals(data.getBatchStatus())) {
            ret.put(TAG_MSG, "该批次的当前状态不允许过账！");
            return ret;
        }

        // 1. 修改批次状态
        data.setBatchStatus(EnumBatchStatus.POSTED.name());

        // 2. 修改订单状态
        String indentSql = new StringBuilder()//
                .append("update ZL_INDENT")//
                .append(" set AP_SETTLEMENT='").append(EnumYesno.YES).append("'")//
                .append(" where ID in (")//
                .append("  select INDENT_ID from ZL_AP_INDENT")//
                .append("  where AP_BATCH_ID=?")//
                .append(")").toString();
        sqlDao.execUpdate(indentSql, new Object[] { id });

        List<ApIndent> apIndentList = apIndentService.findApIndentByBatchId(id);
        // 3. 写账号流水表(经理人、工人)
        managerTradeService.onApBatchPost(data, apIndentList);
        workerTradeService.onApBatchPost(data, apIndentList);

        ret.put(TAG_MSG, "过账成功！");
        ret.put(TAG_SUCCESS, true);
        return ret;
    }

    private List<Indent> findIndentList(ApBatch data) {

        Validate.notNull(data.getBeginDate(), "结算期间的开始日期不能为空");
        Validate.notNull(data.getEndDate(), "结算期间的截至日期不能为空");

        StringBuilder sql = new StringBuilder()//
                .append("select indent.ID, indent.CODE1")//
                .append(", FINISH_DATE").append(", wka.NAME1 as WORKER_NAME1").append(", mgra.NAME1 as MANAGER_NAME1") //
                .append(", SUM, WORKER_FEE, MANAGER_FEE")//
                .append(" from ZL_INDENT indent")//
                .append(" left join ZL_WORKER wk on wk.ID = indent.WORKER_ID")
                .append(" left join ZL_ACCOUNT wka on wka.WORKER_ID = wk.ID")
                .append(" left join ZL_MANAGER mgr on mgr.ID = indent.MANAGER_ID")
                .append(" left join ZL_ACCOUNT mgra on mgra.MANAGER_ID = mgr.ID").append(" WHERE indent.ID not in (")//
                .append("  select INDENT_ID from ZL_AP_INDENT")//
                .append(")")//
                ;//
        QueryInfo queryInfo = QueryInfoBuilder.ins(sql.toString()) //
                .and("AP_SETTLEMENT", CompareOps.NE, EnumYesno.YES.name())
                .and("indent.STATUS", CompareOps.EQ, EnumIndentStatus.NORMAL.name())
                .and("EXECUTE_STATUS", CompareOps.EQ, EnumExecuteStatus.AFTER.name())
                .and("FINISH_DATE", CompareOps.GE, data.getBeginDate()) //
                .and("FINISH_DATE", CompareOps.LT, DateUtil.addDays(data.getEndDate(), 1))//
                .build();

        List<Indent> _list = sqlDao.listAll(queryInfo.getSql(), Indent.class, queryInfo.getParArr());

        return _list;
    }

    private List<Indent> findIndentListByIds(List<String> indentIds) {
        List<Indent> ret = New.list();
        if (indentIds == null || indentIds.size() == 0) {
            return ret;
        }

        StringBuilder sql = new StringBuilder()//
                .append("select ID, CODE1")//
                .append(", WORKER_ID, MANAGER_ID")//
                .append(", SUM, WORKER_FEE, MANAGER_FEE, PROFIT")//
                .append(" from ZL_INDENT")//
                .append(" where ID in (");

        int i = 0, indentSize = indentIds.size();

        for (; i < indentSize; i++) {
            if (i >= 999) {
                break;
            }
            sql.append("?,");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");
        Object[] params = null;
        if (indentSize> i) {
            params = indentIds.subList(0, i).toArray();
        } else {
            params = indentIds.toArray();
        }

        ret = sqlDao.listAll(sql.toString(), Indent.class, params);

        if (indentSize> i) {
            ret.addAll(this.findIndentListByIds(indentIds.subList(i, indentSize)));
        }

        return ret;
    }

    /**
     * 将订单列表中的 经理人与工人列表对应
     * 
     * @param indentList
     * @return
     */
    private Map<String, Set<String>> mgr2WkSet(List<Indent> indentList) {
        Map<String, Set<String>> mgr2Wk = New.linkedMap();
        for (Indent indent : indentList) {
            String managerId = indent.getManagerId();
            String workerId = indent.getWorkerId();

            // 将 managerId与workerId列表对应
            Set<String> wkSet = mgr2Wk.get(managerId);
            if (wkSet == null) {
                wkSet = New.linkedSet();
                mgr2Wk.put(managerId, wkSet);
            }

            wkSet.add(workerId);
        }
        return mgr2Wk;
    }

    /**
     * 将订单列表中的工人与订单列表对应
     * 
     * @param indentList
     * @return
     */
    private Map<String, List<Indent>> wk2IndentList(List<Indent> indentList) {

        Map<String, List<Indent>> wk2Indent = New.linkedMap();
        for (Indent indent : indentList) {
            String workerId = indent.getWorkerId();

            // 将workerId与订单列表对应
            List<Indent> wkIndentList = wk2Indent.get(workerId);
            if (wkIndentList == null) {
                wkIndentList = New.list();
                wk2Indent.put(workerId, wkIndentList);
            }
            wkIndentList.add(indent);
        }

        return wk2Indent;
    }
}