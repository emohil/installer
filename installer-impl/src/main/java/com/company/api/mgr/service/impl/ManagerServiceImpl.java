package com.company.api.mgr.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.account.service.AccountService;
import com.company.api.account.service.EnumAccountStatus;
import com.company.api.account.service.EnumAccountType;
import com.company.api.account.service.EnumCheckStatus;
import com.company.api.bankcard.service.BankCardService;
import com.company.api.dict.service.EnumTradeType;
import com.company.api.dict.service.EnumYesno;
import com.company.api.district.service.CityService;
import com.company.api.district.service.ProvService;
import com.company.api.fs.service.EnumSlaveStatus;
import com.company.api.fs.service.FileManagerService;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.fw.service.impl.query.QueryInfo;
import com.company.api.fw.service.impl.query.QueryInfoBuilder;
import com.company.api.fw.util.MessagePush;
import com.company.api.indent.service.EnumJpushType;
import com.company.api.mgr.service.ManagerService;
import com.company.api.wk.service.WkMgrSlaveService;
import com.company.api.wk.service.WorkerService;
import com.company.dto.mgr.MgrData;
import com.company.dto.role.ManagerData;
import com.company.po.account.Account;
import com.company.po.bankcard.BankCard;
import com.company.po.district.City;
import com.company.po.district.Prov;
import com.company.po.mgr.Manager;
import com.company.po.wk.WkMgrSlave;
import com.company.po.wk.Worker;
import com.company.sf.account.ManagerSf;
import com.company.context.ThreadContext;
import com.company.dto.Filter;
import com.company.dto.Order;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.mgr.dao.ManagerDao;
import com.company.util.DigestUtil;
import com.company.util.Dto;
import com.company.util.RandomUtil;
import com.company.util.StringUtil;

@Service(ManagerService.BEAN_NAME)
public class ManagerServiceImpl extends StringIdBaseServiceImpl<Manager> implements ManagerService {

    public static final String EXT1 = "ACCOUNT";

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    private AccountService accountService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private ProvService provService;

    @Autowired
    private CityService cityService;

    @Autowired
    private FileManagerService fileManagerService;

    @Autowired
    private BankCardService bankCardService;

    @Autowired
    private WkMgrSlaveService slaveService;

    @Autowired
    public void setBaseDao(ManagerDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public List<Manager> list(ManagerSf sf, int start, int limit, List<Order> orders) {
        QueryInfo queryInfo = prepareQuery(sf).order(orders).build();

        List<Manager> _list = sqlDao.list(queryInfo.getSql(), start, limit, Manager.class, queryInfo.getParArr());
        List<Manager> list = new ArrayList<Manager>();
        for (Manager manager : _list) {
            String accountId = manager.getAccountId();
            manager.setAccount(accountService.find(accountId));
            list.add(manager);
        }

        return list;
    }

    @Override
    public int count(ManagerSf sf) {
        QueryInfo queryInfo = prepareQuery(sf).build();
        return sqlDao.count(queryInfo.getCountSql(), queryInfo.getParArr());
    }

    private QueryInfoBuilder prepareQuery(ManagerSf sf) {
        if (sf == null) {
            sf = new ManagerSf();
        }
        if (sf.getAccount() == null) {
            sf.setAccount(new Account());
        }
        String sql = new StringBuilder("select manager.CODE1,manager.ACCOUNT_ID,manager.STATUS,manager.ROLE_STATUS,manager.VERIFIER,manager.ID, manager.STARS, manager.TOTAL_INDENT from ZL_MANAGER manager, ZL_ACCOUNT account where manager.ID = account.MANAGER_ID and ")//
        .append(" account.NAME1 LIKE '%" + StringUtil.defaultString(sf.getAccount().getName1(), "") + "%' and ")//
        .append(" account.MOBILE LIKE '%" + StringUtil.defaultString(sf.getAccount().getMobile(), "") + "%'")//
        .toString();//

        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql) //
                .andContains("manager.SERVE_CITY", sf.getServeCity())// 经理人服务地区
                .andContains("manager.CODE1", sf.getCode1()) // 经理人工号
                .andEq("manager.STATUS", sf.getStatus()) // 审核状态
                .andEq("manager.ROLE_STATUS", sf.getRoleStatus()) // 角色状态
                .andEq("manager.IS_HELPER", sf.getIsHelper())
                ;
        return builder;
    }

    @Override
    public Manager findByManagerId(String id) {
        Manager manager = super.find(id);
        City city = cityService.find(manager.getServeCity());
        if (city != null) {
            String serviceCity = "";
            Prov prov = provService.find(city.getProvId());
            if (prov != null) {
                serviceCity = prov.getName1() + "--" + city.getName1();
            } else {
                serviceCity = city.getName1();
            }
            manager.setServiceCity(serviceCity);
        }
        Account account = accountService.find(manager.getAccountId());
        if (account.getIdImgUrl() != null) {
            account.setIdImg(fileManagerService.getFileUrlByFilepath(account.getIdImgUrl()));
        }
        if (StringUtil.isNotBlank(manager.getCharterImg())) {
            manager.setCharter(fileManagerService.getFileUrlByFilepath(manager.getCharterImg()));
        }
        if (StringUtil.isNotBlank(manager.getOrgCodeImg())) {
            manager.setOrgCode(fileManagerService.getFileUrlByFilepath(manager.getOrgCodeImg()));
        }
        if (StringUtil.isNotBlank(manager.getTaxImg())) {
            manager.setTax(fileManagerService.getFileUrlByFilepath(manager.getTaxImg()));
        }
        manager.setAccount(account);
        return manager;
    }

    @Override
    public Manager update(Manager entity) {

        return super.update(entity);
    }

    @Override
    public void saveManager(Manager manager, Account account) {
        if (account.getIdNum() != null && account.getIdNum().length() >= 18) {
            manager.setCode1("M" + account.getIdNum().substring(0, 5) + RandomUtil.getRandomStr(4));
        }
        if (account.getWorkerType().equals(0)) {
            String serviceCity = manager.getServeCity().substring(0, 4);
            manager.setServeCity(serviceCity);

        }
        manager.setStatus(EnumCheckStatus.UNCHECK.name());
        manager.setRoleStatus(EnumAccountStatus.ENABLED.name());
        manager.setAccountId(account.getId());
        manager.setIsHelper(0);
        manager.setStars(5);
        manager.setCreditScore(new BigDecimal(5));

        super.save(manager);
        account.setManagerType(1);
        account.setManagerId(manager.getId());
        accountService.update(account, "pwd");
    }

    @Override
    public void createHelper(Manager manager, Account account) {
        manager.setCode1("M_HELPER_" + manager.getServeCity());
        manager.setRoleStatus(EnumAccountStatus.ENABLED.name());
        manager.setAccountId(account.getId());
        manager.setStatus(EnumCheckStatus.PASS.name());
        manager.setStars(5);
        super.save(manager);

        account.setName1("小助手");
        account.setManagerType(1);
        account.setManagerId(manager.getId());

        accountService.update(account, "pwd");
    }

    @Override
    public void bindBankCard(BankCard bankCard, Manager manager) {
        bankCardService.save(bankCard);
        manager.setBankId(bankCard.getId());
        update(manager);
    }

    @Override
    public Dto relieveBankCard(String tradePwd, Manager manager) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        // 验证密码
        if (!DigestUtil.md5(tradePwd).equals(manager.getTradePwd())) {
            rt.put(TAG_MSG, "密码不正确，请重新输入密码");
            return rt;
        }
        bankCardService.delByOwnerId(manager.getId());
        manager.setBankId("");
        update(manager);
        rt.put(TAG_MSG, "成功解除绑定");
        rt.put(TAG_SUCCESS, true);
        return rt;
    }

    @Override
    public void reSetRank() {

        // 更新经理人的排名
        QueryInfo queryInfo = rankPrepareQuery().build();
        sqlDao.execUpdate(queryInfo.getSql(), queryInfo.getParArr());
    }

    private QueryInfoBuilder rankPrepareQuery() {
        StringBuilder sql = new StringBuilder("UPDATE ZL_MANAGER INNER JOIN (")//
        .append(" SELECT @rownum/*'*/:=/*'*/@rownum+1 as ROWNUM, ID from (")//
        .append(" SELECT @rownum/*'*/:=/*'*/0,ID FROM ZL_MANAGER")//
        .append(" LEFT JOIN (SELECT MANAGER_ID, SUM(CUR_AMT) AS AMT")//
        .append(" FROM ZL_MANAGER_TRADE WHERE 1=1 AND TRADE_TYPE=? AND date_format(TRADE_DATE,'%Y-%m')=date_format(now(),'%Y-%m') GROUP BY MANAGER_ID) SUM_TRADE");//
        List<Object> sqlPars = new ArrayList<Object>();
        sqlPars.add(EnumTradeType.EARNING.name());
        sql.append(" ON ID = MANAGER_ID")//
        .append(" ORDER BY AMT DESC")//
        .append(" ) RAND_MANAGER")//
        .append(" ) RANK")//
        .append(" SET PERIOD_RANK = RANK.ROWNUM")//
        .append(" WHERE RANK.ID = ZL_MANAGER.ID");

        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql.toString()).sqlPars(sqlPars);
        return builder;
    }

    @Override
    public List<Manager> findManagers(Manager managerQuery, int limit) {

        QueryInfo queryInfo = findManagersPrepareQuery(managerQuery, limit).build();
        return sqlDao.listAll(queryInfo.getSql(), Manager.class, queryInfo.getParArr());

    }

    private QueryInfoBuilder findManagersPrepareQuery(Manager managerQuery, int limit) {
        StringBuilder sql = new StringBuilder("select * from ZL_MANAGER WHERE 1=1");//
        sql.append(" AND SERVE_CITY = ?");//
        List<Object> sqlPars = new ArrayList<Object>();
        sqlPars.add(managerQuery.getServeCity());
        sql.append(" AND `STATUS` = ?");//
        sqlPars.add(managerQuery.getStatus());
        sql.append(" AND IS_HELPER = ?");//
        sqlPars.add(managerQuery.getIsHelper());
        // 星级大于等于三
        if (managerQuery.getStars() >= 3) {
            sql.append(" AND STARS >= ").append(managerQuery.getStars());//
        } else {
            sql.append(" AND STARS < ").append(managerQuery.getStars());//
        }
        sql.append(" AND ROLE_STATUS = ? order by rand() limit ").append(limit);
        sqlPars.add(managerQuery.getRoleStatus());
        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql.toString()).sqlPars(sqlPars);
        return builder;
    }

    @Override
    public Dto acceptWorker(Worker worker) {
        Dto dto = new Dto();
        dto.put(TAG_SUCCESS, false);
        String sql = "UPDATE ZL_WORKER SET MANAGER_IDEA = ? WHERE ID = ?";
        int i = sqlDao.execUpdate(sql, worker.getManagerIdea(), worker.getId());
        if (i == 1) {
            dto.put(TAG_SUCCESS, true);
        }
        Account workerAccount = accountService.findOne(Filter.eq("workerId", worker.getId()));
        Account managerAccount = (Account) ThreadContext.getContextBean();
        Worker worker1 = workerService.find(worker.getId());
        Map<String, String> extras = new HashMap<String, String>();
        extras.put("id", "");
        extras.put("accountType", EnumAccountType.WORKER.name());

        List<String> registrationIds = new ArrayList<String>();
        if (StringUtil.isNotBlank(workerAccount.getRegistrationId())) {
            registrationIds.add(workerAccount.getRegistrationId());
        }
        MessagePush push = null;

        WkMgrSlave slave = slaveService.findByMIdAndWId(worker1.getManagerId(), worker1.getId(), EnumSlaveStatus.APPLY.name());
        if (EnumYesno.NO.name().equals(worker.getManagerIdea())) {
            extras.put("pushType", EnumJpushType.MANAGER_CHOOSE.name());
            push = new MessagePush("经理人" + managerAccount.getName1() + "还未处理您的申请，重新选择经理人吧", "众联工匠", extras);
            slave.setStatus(EnumSlaveStatus.NEGLECT.name());
        } else {
            extras.put("pushType", "");
            push = new MessagePush("经理人" + managerAccount.getName1() + "已同意您加入其团队", "众联工匠", extras);
            slave.setStatus(EnumSlaveStatus.AGREE.name());
        }
        if (registrationIds.size() > 0) {
            push.pushMsgToRegistrationIds(registrationIds);
        }

        slaveService.update(slave);
        return dto;
    }

    @Override
    public List<ManagerData> queryPassedManagers() {
        String sql = new StringBuilder() //
        .append("select manager.ID as V, account.NAME1 as N") //
        .append(" from ZL_MANAGER manager left join ZL_ACCOUNT account on manager.ACCOUNT_ID = account.ID") //
        .append(" where manager.STATUS='")//
        .append(EnumCheckStatus.PASS.name())//
        .append("'") //
        .append(" and manager.ROLE_STATUS='")//
        .append(EnumAccountStatus.ENABLED.name())//
        .append("'") //
        .append(" order by CODE1 ASC") //
        .toString();

        List<ManagerData> _list = sqlDao.listAll(sql, ManagerData.class);

        return _list;
    }

    @Override
    public List<MgrData> findListByWorkerId(String workerId) {
        String sql = "SELECT account.NAME1 name1, ACCOUNT.MOBILE mobile, wkmgr.desc1 desc1,"
                +" wkmgr.apply_date applyDate, wkmgr.status status FROM zl_account account, ("
                +"SELECT * from zl_wk_mgr_slave WHERE WORKER_ID=?"
                +" AND `STATUS` <> 'NEGLECT' ORDER BY APPLY_DATE DESC) wkmgr where"
                +" ACCOUNT.MANAGER_ID=wkmgr.MANAGER_ID";

        return sqlDao.listAll(sql, MgrData.class, workerId);
    }

    @Override
    public void updateManager(Manager manager) {

        if (manager.getStatus().equals(EnumCheckStatus.PASS.name())) {
            Account account = accountService.find(manager.getAccountId());
            if (account.getWorkerType().equals(1) && StringUtil.isNotBlank(account.getWorkerId())) {
                Worker worker = workerService.find(account.getWorkerId());
                if (!worker.getManagerId().equals(manager.getId())) {
                    Manager oldManager = find(worker.getManagerId());
                    Account oldAccount = accountService.find(oldManager.getAccountId());
                    //给以前的经理人推送,修改wkMgrSlave状态
                    WkMgrSlave wkMgrSlave = slaveService.findByMIdAndWId(oldManager.getId(), worker.getId(), EnumSlaveStatus.AGREE.name());
                    wkMgrSlave.setStatus(EnumSlaveStatus.ABOLISH.name());
                    slaveService.update(wkMgrSlave);
                    {// 推送给原经理人，有工匠退出
                        Map<String, String> extras = new HashMap<String, String>();
                        extras.put("id", "");
                        extras.put("pushType", EnumJpushType.WORKER_LIST.name());
                        List<String> registrationIds = new ArrayList<String>();
                        if (StringUtil.isNotBlank(oldAccount.getRegistrationId())) {
                            registrationIds.add(oldAccount.getRegistrationId());
                            MessagePush push = new MessagePush("工匠" + account.getName1() + "已退出您的团队", "众联工匠", extras);
                            push.pushMsgToRegistrationIds(registrationIds);
                        }
                    
                    //把工人归到自己名下
                    
                    String sql = "update ZL_WORKER set MANAGER_ID=?, MANAGER_IDEA=? where ID=?";
                    sqlDao.execUpdate(sql, manager.getId(), EnumYesno.YES.name(), worker.getId());
                    
                    //创建一条新的slave记录
                    WkMgrSlave slave = new WkMgrSlave();
                    slave.setApplyDate(new Date());
                    slave.setManagerId(manager.getId());
                    slave.setWorkerId(worker.getId());
                    slave.setStatus(EnumSlaveStatus.AGREE.name());
                    slaveService.save(slave);
                }
            }
        }
    }
        update(manager);
}

    @Override
    public void updateDepositAmt(Manager manager) {
        String sql = "update ZL_MANAGER set DEPOSIT_AMT = ? where ID = ?";
        sqlDao.execUpdate(sql, manager.getDepositAmt(), manager.getId());
    }
}