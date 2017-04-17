package com.company.api.account.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.account.service.AccountService;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.fw.service.impl.query.QueryInfo;
import com.company.api.fw.service.impl.query.QueryInfoBuilder;
import com.company.po.account.Account;
import com.company.sf.account.AccountSf;
import com.company.dto.Order;
import com.company.repo.account.dao.AccountDao;
import com.company.repo.fw.dao.SqlDao;
import com.company.util.DigestUtil;
import com.company.util.New;
import com.company.util.StringUtil;

@Service(AccountService.BEAN_NAME)
public class AccountServiceImpl extends StringIdBaseServiceImpl<Account>implements AccountService {

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    public void setBaseDao(AccountDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public Account findAccountByMobile(String mobile) {
        String sql = "select * from ZL_ACCOUNT where MOBILE=?";

        return sqlDao.unique(sql, Account.class, new Object[] { mobile });
    }

    @Override
    public void createAccount(Account account) {

        if (StringUtil.isEmpty(account.getAccount())) {
            account.setAccount(account.getMobile());
        }
        String pwd = StringUtil.defaultString(account.getPwd(), UUID.randomUUID().toString());
        account.setPwd(DigestUtil.md5(pwd));
        account.setManagerType(0);
        account.setWorkerType(0);
        
        account.setBuildNum("");
        account.setSex("");
        account.setWorkerId("");
        account.setManagerId("");
        account.setAvatar("");
        account.setModifyBy("");
        account.setCreateBy("");

        this.save(account);
    }

    @Override
    public List<Account> list(AccountSf sf, int start, int limit, List<Order> orders) {
        QueryInfo queryInfo = prepareQuery(sf).order(orders).build();

        List<Account> _list = sqlDao.list(queryInfo.getSql(), start, limit, Account.class, queryInfo.getParArr());

        return _list;
    }

    @Override
    public int count(AccountSf sf) {
        QueryInfo queryInfo = prepareQuery(sf).build();
        return sqlDao.count(queryInfo.getCountSql(), queryInfo.getParArr());
    }

    private QueryInfoBuilder prepareQuery(AccountSf sf) {
        if (sf == null) {
            sf = new AccountSf();
        }
        String sql = "select * from ZL_APARTY where 1=1";
        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql) //
        // .andContains("NAME1", sf.getName1()) //
        // .andEq("REGION_PROV", sf.getRegionProv())
        // .andEq("REGION_CITY", sf.getRegionCity())
        // .andEq("REGION_DIST", sf.getRegionDist())
        // .andLe("CRT_DATE", sf.getCrtDateBegin())
        // .andLe("CRT_DATE", sf.getCrtDateEnd())
        ;
        return builder;
    }

    @Override
    public Map<String, String> workerId2Display(List<Account> accountList) {
        Map<String, String> ret = New.hashMap();
        for (Account a : accountList) {
            ret.put(a.getWorkerId(), a.getName1());
        }
        return ret;
    }

    @Override
    public Map<String, String> managerId2Display(List<Account> accountList) {
        Map<String, String> ret = New.hashMap();
        for (Account a : accountList) {
            ret.put(a.getManagerId(), a.getName1());
        }
        return ret;
    }

    @Override
    public void updateLastLoginTime(String id) {
        String sql = "UPDATE ZL_ACCOUNT SET LAST_LOGIN_DATE = ? WHERE ID = ?";
        sqlDao.execUpdate(sql, new Date(), id);
    }
    
    @Override
    public Account findByWorkerId(String workerId) {
        String sql = "select * from ZL_ACCOUNT where WORKER_ID=?";
        return sqlDao.unique(sql, Account.class, workerId);
    }
    
    @Override
    public Account findByManagerId(String managerId) {
        String sql = "select * from ZL_ACCOUNT where MANAGER_ID=?";
        return sqlDao.unique(sql, Account.class, managerId);
    }

    @Override
    public void updateLastLoginType(Account account) {
        String sql = "UPDATE ZL_ACCOUNT SET LAST_LOGIN_TYPE = ? WHERE ID = ?";
        sqlDao.execUpdate(sql, account.getLastLoginType(), account.getId());
    }

    @Override
    public void emptyRegistrationId(String registrationId, String id) {
        String sql = "UPDATE ZL_ACCOUNT SET REGISTRATION_ID = '' WHERE REGISTRATION_ID = ? AND ID<>?";
        sqlDao.execUpdate(sql, registrationId, id);
    }
}