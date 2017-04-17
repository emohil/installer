package com.company.api.mgr.service;

import java.math.BigDecimal;
import java.util.List;

import com.company.po.ap.ApBatch;
import com.company.po.ap.ApIndent;
import com.company.po.mgr.Manager;
import com.company.po.mgr.ManagerTrade;
import com.company.sf.account.ManagerTradeSf;
import com.company.api.fw.service.StringIdBaseService;
import com.company.dto.Order;
import com.company.util.Dto;

public interface ManagerTradeService extends StringIdBaseService<ManagerTrade> {

    String BEAN_NAME = "managerTradeService";
    
    int count(ManagerTradeSf sf);

    List<ManagerTrade> list(ManagerTradeSf sf, int start, int limit, List<Order> orders);

    void onApBatchPost(ApBatch apBatch, List<ApIndent> apIndentList);

    /**
     * 获取最近的交易明细记录
     * 
     * @param managerId
     * @return
     */
    ManagerTrade findRecentlyTrade(String managerId);

    /**
     * 提现
     */
    Dto cashTrade(String managerId, BigDecimal tradeAmt);
    
    /**
     * 统计当期收入
     * @param managerId
     * @return
     */
    BigDecimal monthEarning(String managerId);
    
    /**
     * 累计交易金额
     * @return
     */
    BigDecimal totalTradeAmt();
    
    void doUpdate(ManagerTrade data, Manager manager, ManagerTrade managerTrade);
    
    /**
     * 统计本月提现次数
     * @param managerId
     * @return
     */
    int monthDrawingsCount(String managerId);
}