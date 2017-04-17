package com.company.api.mgr.service;

import java.math.BigDecimal;
import java.util.List;

import com.company.po.ap.ApBatch;
import com.company.po.ap.ApIndent;
import com.company.po.indent.Indent;
import com.company.po.mgr.Manager;
import com.company.po.mgr.ManagerDeposit;
import com.company.api.fw.service.StringIdBaseService;
import com.company.dto.Order;

public interface ManagerDepositService extends StringIdBaseService<ManagerDeposit> {

    String BEAN_NAME = "managerDepositService";
    
    List<ManagerDeposit> list(ManagerDeposit md, int start, int limit, List<Order> orders);

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
     * @param manager
     *            经理人信息
     * @param apBatch
     *            结算批次
     * @param apIndent
     *            批次订单
     * @param depositAmt
     *            保证金
     */
    void saveDeposit(Manager manager, ApBatch apBatch, ApIndent apIndent, BigDecimal depositAmt);

    /**
     * 扣除保证金
     * @param deductDepositManager
     *            金额
     * @param manager
     *            角色
     * @param indent 
     */           
    void deductDeposit(BigDecimal deductDepositManager, Manager manager, Indent indent);

}