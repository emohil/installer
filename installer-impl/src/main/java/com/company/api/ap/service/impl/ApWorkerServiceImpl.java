package com.company.api.ap.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.account.service.AccountService;
import com.company.api.ap.service.ApManagerService;
import com.company.api.ap.service.ApWorkerService;
import com.company.api.fw.service.impl.PagerBaseServiceImpl;
import com.company.api.fw.service.impl.query.CompareOps;
import com.company.api.fw.service.impl.query.QueryInfoBuilder;
import com.company.po.account.Account;
import com.company.po.ap.ApWorker;
import com.company.repo.ap.dao.ApWorkerDao;
import com.company.repo.fw.dao.SqlDao;

@Service(ApWorkerService.BEAN_NAME)
public class ApWorkerServiceImpl extends PagerBaseServiceImpl<ApWorker, ApWorker>//
        implements ApWorkerService {

    @Autowired
    private SqlDao sqlDao;
    
    @Autowired
    private AccountService accountService;

    @Autowired
    private ApManagerService apManagerService;

    @Autowired
    public void setBaseDao(ApWorkerDao dao) {
        super.setBaseDao(dao);
    }
    
    @Override
    public void transformList(List<ApWorker> list, ApWorker sf) {
        String apBatchId = sf.getApBatchId();
        List<Account> mgrAccountList = apManagerService.findManagerListByApBatchId(apBatchId);
        Map<String, String> mgr2disp = accountService.managerId2Display(mgrAccountList);

        List<Account> wkAccountList = this.findWorkerListByApBatchId(apBatchId);
        Map<String, String> wk2disp = accountService.workerId2Display(wkAccountList);

        for (ApWorker apWorker : list) {
            apWorker.setManagerIdDisp(mgr2disp.get(apWorker.getManagerId()));
            apWorker.setWorkerIdDisp(wk2disp.get(apWorker.getWorkerId()));
        }
    }

    @Override
    public QueryInfoBuilder prepareQuery(ApWorker sf) {
        if (sf == null) {
            sf = new ApWorker();
        }
        StringBuilder sql = new StringBuilder()//
                .append("select * from ZL_AP_WORKER")//
                .append(" WHERE 1=1");

        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql.toString())//
                .and("AP_BATCH_ID", CompareOps.EQ, sf.getApBatchId())//
                .and("MANAGER_ID", CompareOps.EQ, sf.getManagerId())//
                .orderAsc("LINE_NO")//
                ;

        return builder;
    }

    @Override
    public void deleteByBatchId(String apBatchId) {
        String sql = "delete from ZL_AP_WORKER where AP_BATCH_ID=?";
        sqlDao.execUpdate(sql, new Object[] { apBatchId });
    }

    @Override
    public List<Account> findWorkerListByApBatchId(String apBatchId) {
        String sql = new StringBuilder()//
                .append("select WORKER_ID, NAME1")//
                .append(" from ZL_ACCOUNT") //
                .append(" where WORKER_ID in (")//
                .append("  select WORKER_ID from ZL_AP_WORKER")//
                .append("  where AP_BATCH_ID=?") //
                .append(")")//
                .toString()//
                ;
        return sqlDao.listAll(sql, Account.class, new Object[] { apBatchId });
    }
}