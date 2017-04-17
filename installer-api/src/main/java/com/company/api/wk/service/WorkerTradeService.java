package com.company.api.wk.service;

import java.math.BigDecimal;
import java.util.List;

import com.company.po.ap.ApBatch;
import com.company.po.ap.ApIndent;
import com.company.po.wk.Worker;
import com.company.po.wk.WorkerTrade;
import com.company.sf.account.WorkerTradeSf;
import com.company.api.fw.service.StringIdBaseService;
import com.company.dto.Order;
import com.company.util.Dto;

public interface WorkerTradeService extends StringIdBaseService<WorkerTrade> {

    String BEAN_NAME = "workerTradeService";
    
    int count(WorkerTradeSf sf);

    List<WorkerTrade> list(WorkerTradeSf sf, int start, int limit, List<Order> orders);

    /**
     * 结算批次过账操作
     * 
     * @param apBatch
     * @param apIndentList
     */
    void onApBatchPost(ApBatch apBatch, List<ApIndent> apIndentList);

    /**
     * 获取最近的交易明细记录
     * 
     * @param workerId
     * @return
     */
    WorkerTrade findRecentlyTrade(String workerId);

    /**
     * 提现
     */
    Dto cashTrade(String workerId, BigDecimal tradeAmt);

    /**
     * 当期累计收入
     * @param workerId
     * @return
     */
    BigDecimal monthEarning(String workerId);
    
    /**
     * 累计交易金额
     * @return
     */
    BigDecimal totalTradeAmt();
    
    void doUpdate(WorkerTrade data, Worker worker, WorkerTrade workerTrade);
    
    /**
     * 统计当月提现次数
     * @param workerId
     * @return
     */
    int monthDrawingsCount(String workerId);
}