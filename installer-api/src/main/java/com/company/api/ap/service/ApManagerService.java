package com.company.api.ap.service;

import java.util.List;

import com.company.po.account.Account;
import com.company.po.ap.ApManager;
import com.company.api.fw.service.PagerService;
import com.company.api.fw.service.StringIdBaseService;

public interface ApManagerService extends StringIdBaseService<ApManager>, //
        PagerService<ApManager, ApManager> {

    String BEAN_NAME = "apManagerService";

    /**
     * 根据批次ID删除数据
     * 
     * @param apBatchId
     */
    void deleteByBatchId(String apBatchId);

    /**
     * 查找结算中批次经理人对应的账号列表
     * 
     * @param apBatchId
     * @return
     */
    List<Account> findManagerListByApBatchId(String apBatchId);
}