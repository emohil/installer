package com.company.api.mgr.service;

import java.util.List;

import com.company.dto.mgr.MgrData;
import com.company.dto.role.ManagerData;
import com.company.po.account.Account;
import com.company.po.bankcard.BankCard;
import com.company.po.mgr.Manager;
import com.company.po.wk.Worker;
import com.company.sf.account.ManagerSf;
import com.company.api.fw.service.StringIdBaseService;
import com.company.dto.Order;
import com.company.util.Dto;

public interface ManagerService extends StringIdBaseService<Manager> {

    String BEAN_NAME = "managerService";

    int count(ManagerSf sf);

    List<?> list(ManagerSf sf, int start, int limit, List<Order> orders);

    void saveManager(Manager manager, Account account);

    void createHelper(Manager manager, Account account);

    void bindBankCard(BankCard bankCard, Manager manager);

    Dto relieveBankCard(String tradePwd, Manager manager);

    void reSetRank();
    
    Manager findByManagerId(String id);

    /**
     * 查经理人  随机出现经理人列表
     * @param managerQuery
     * @param limit
     * @return
     */

    List<Manager> findManagers(Manager managerQuery, int limit);

    Dto acceptWorker(Worker worker);

    List<ManagerData> queryPassedManagers();
    
    List<MgrData> findListByWorkerId(String workerId);
    
    void updateManager(Manager manager);

    void updateDepositAmt(Manager manager);

}