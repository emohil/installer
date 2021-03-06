package com.company.api.ap.service;

import java.util.List;

import com.company.po.account.Account;
import com.company.po.ap.ApWorker;
import com.company.api.fw.service.PagerService;
import com.company.api.fw.service.StringIdBaseService;

public interface ApWorkerService extends StringIdBaseService<ApWorker>, //
        PagerService<ApWorker, ApWorker> {

    String BEAN_NAME = "apWorkerService";

    /**
     * 根据批次ID删除数据
     * 
     * @param apBatchId
     */
    void deleteByBatchId(String apBatchId);

    /**
     * 查找结算中批次工人对应的账号列表
     * 
     * @param apBatchId
     * @return
     */
    List<Account> findWorkerListByApBatchId(String apBatchId);
}