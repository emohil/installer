package com.company.api.wk.service;

import java.math.BigDecimal;
import java.util.List;

import com.company.po.ap.ApBatch;
import com.company.po.ap.ApIndent;
import com.company.po.indent.Indent;
import com.company.po.wk.Worker;
import com.company.po.wk.WorkerDeposit;
import com.company.api.fw.service.StringIdBaseService;
import com.company.dto.Order;

public interface WorkerDepositService extends StringIdBaseService<WorkerDeposit> {

    String BEAN_NAME = "workerDepositService";

    List<WorkerDeposit> list(WorkerDeposit wd, int start, int limit, List<Order> orders);

    /**
     * 计算保证金金额
     * 
     * @param preAmt
     *            已有保证金
     * @param tradeAmt
     *            本次流水金额
     * @return
     */
    BigDecimal calcDeposit(BigDecimal preAmt, BigDecimal tradeAmt);

    /**
     * 保存保证金
     * 
     * @param worker
     *            工人信息
     * @param apBatch
     *            结算批次
     * @param apIndent
     *            批次订单
     * @param depositAmt
     *            保证金
     */
    void saveDeposit(Worker manager, ApBatch apBatch, ApIndent apIndent, BigDecimal depositAmt);
    
    /**
     * 扣除保证金
     * @param deductDepositWorker
     *             金额
     * @param worker
     *             角色
     * @param indent 
     *             订单
     */ 
    void deductDeposit(BigDecimal deductDepositWorker, Worker worker, Indent indentO);
}