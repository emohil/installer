package com.company.api.account.service;

import java.util.List;
import java.util.Map;

import com.company.po.account.Account;
import com.company.sf.account.AccountSf;
import com.company.api.fw.service.StringIdBaseService;
import com.company.dto.Order;

public interface AccountService extends StringIdBaseService<Account> {

    String BEAN_NAME = "accountService";

    Account findAccountByMobile(String mobile);

    /**
     * 根据已有的信息，创建新账号
     * 
     * @param account
     */
    void createAccount(Account account);

    /**
     * 根据已有的信息，创建新账号
     * 
     * @param account
     */

    int count(AccountSf sf);

    List<?> list(AccountSf sf, int start, int limit, List<Order> orders);

    /**
     * 工人ID 到显示名 之间的映射
     * 
     * @param accountList
     * @return
     */
    Map<String, String> workerId2Display(List<Account> accountList);

    /**
     * 经理人ID 到显示名 之间的映射
     * 
     * @param accountList
     * @return
     */
    Map<String, String> managerId2Display(List<Account> accountList);

    void updateLastLoginTime(String id);
    
    Account findByWorkerId(String workerId);
    
    Account findByManagerId(String managerId);

    void updateLastLoginType(Account account);

    void emptyRegistrationId(String registrationId, String id);
    
}