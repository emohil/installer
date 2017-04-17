package com.company.api.ap.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.account.service.AccountService;
import com.company.api.ap.service.ApIndentService;
import com.company.api.ap.service.ApManagerService;
import com.company.api.ap.service.ApWorkerService;
import com.company.api.fw.service.impl.PagerBaseServiceImpl;
import com.company.api.fw.service.impl.query.CompareOps;
import com.company.api.fw.service.impl.query.QueryInfoBuilder;
import com.company.po.account.Account;
import com.company.po.ap.ApIndent;
import com.company.repo.ap.dao.ApIndentDao;
import com.company.repo.fw.dao.SqlDao;

@Service(ApIndentService.BEAN_NAME)
public class ApIndentServiceImpl extends PagerBaseServiceImpl<ApIndent, ApIndent>//
        implements ApIndentService {

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ApManagerService apManagerService;

    @Autowired
    private ApWorkerService apWorkerService;

    @Autowired
    public void setBaseDao(ApIndentDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public void transformList(List<ApIndent> list, ApIndent sf) {
        String apBatchId = sf.getApBatchId();
        List<Account> mgrAccountList = apManagerService.findManagerListByApBatchId(apBatchId);
        Map<String, String> mgr2disp = accountService.managerId2Display(mgrAccountList);

        List<Account> wkAccountList = apWorkerService.findWorkerListByApBatchId(apBatchId);
        Map<String, String> wk2disp = accountService.workerId2Display(wkAccountList);

        for (ApIndent apIndent : list) {
            apIndent.setManagerIdDisp(mgr2disp.get(apIndent.getManagerId()));
            apIndent.setWorkerIdDisp(wk2disp.get(apIndent.getWorkerId()));
        }
    }

    @Override
    public QueryInfoBuilder prepareQuery(ApIndent sf) {
        if (sf == null) {
            sf = new ApIndent();
        }
        StringBuilder sql = new StringBuilder()//
                .append("select * from ZL_AP_INDENT")//
                .append(" WHERE 1=1");

        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql.toString())//
                .and("AP_BATCH_ID", CompareOps.EQ, sf.getApBatchId())//
                .and("MANAGER_ID", CompareOps.EQ, sf.getManagerId())//
                .and("WORKER_ID", CompareOps.EQ, sf.getWorkerId())//
                .orderAsc("LINE_NO")//
                ;

        return builder;
    }

    @Override
    public void deleteByBatchId(String apBatchId) {
        String sql = "delete from ZL_AP_INDENT where AP_BATCH_ID=?";
        sqlDao.execUpdate(sql, new Object[] { apBatchId });
    }

    @Override
    public List<ApIndent> findApIndentByBatchId(String apBatchId) {
        String sql = "select * from ZL_AP_INDENT where AP_BATCH_ID=?";

        return sqlDao.listAll(sql, ApIndent.class, new Object[] { apBatchId });
    }
}