package com.company.api.ap.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.account.service.AccountService;
import com.company.api.ap.service.ApManagerService;
import com.company.api.fw.service.impl.PagerBaseServiceImpl;
import com.company.api.fw.service.impl.query.CompareOps;
import com.company.api.fw.service.impl.query.QueryInfoBuilder;
import com.company.po.account.Account;
import com.company.po.ap.ApManager;
import com.company.repo.ap.dao.ApManagerDao;
import com.company.repo.fw.dao.SqlDao;

@Service(ApManagerService.BEAN_NAME)
public class ApManagerServiceImpl extends PagerBaseServiceImpl<ApManager, ApManager>//
        implements ApManagerService {

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    private AccountService accountService;

    @Autowired
    public void setBaseDao(ApManagerDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public void transformList(List<ApManager> list, ApManager sf) {
        String apBatchId = sf.getApBatchId();
        List<Account> mgrAccountList = this.findManagerListByApBatchId(apBatchId);
        Map<String, String> mgr2disp = accountService.managerId2Display(mgrAccountList);

        for (ApManager apManager : list) {
            apManager.setManagerIdDisp(mgr2disp.get(apManager.getManagerId()));
        }
    }

    @Override
    public QueryInfoBuilder prepareQuery(ApManager sf) {
        if (sf == null) {
            sf = new ApManager();
        }
        StringBuilder sql = new StringBuilder()//
                .append("select * from ZL_AP_MANAGER")//
                .append(" WHERE 1=1");

        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql.toString())//
                .and("AP_BATCH_ID", CompareOps.EQ, sf.getApBatchId())//
                .orderAsc("LINE_NO")//
                ;

        return builder;
    }

    @Override
    public void deleteByBatchId(String apBatchId) {
        String sql = "delete from ZL_AP_MANAGER where AP_BATCH_ID=?";
        sqlDao.execUpdate(sql, new Object[] { apBatchId });
    }

    @Override
    public List<Account> findManagerListByApBatchId(String apBatchId) {
        String sql = new StringBuilder()//
                .append("select MANAGER_ID, NAME1")//
                .append(" from ZL_ACCOUNT") //
                .append(" where MANAGER_ID in (")//
                .append("  select MANAGER_ID from ZL_AP_MANAGER")//
                .append("  where AP_BATCH_ID=?") //
                .append(")")//
                .toString()//
                ;
        return sqlDao.listAll(sql, Account.class, new Object[] { apBatchId });
    }
}