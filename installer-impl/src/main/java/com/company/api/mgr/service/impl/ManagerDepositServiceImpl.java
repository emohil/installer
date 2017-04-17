package com.company.api.mgr.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.dict.service.EnumTradeType;
import com.company.api.fw.Constants;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.fw.service.impl.query.QueryInfo;
import com.company.api.fw.service.impl.query.QueryInfoBuilder;
import com.company.api.mgr.service.ManagerDepositService;
import com.company.api.mgr.service.ManagerService;
import com.company.po.ap.ApBatch;
import com.company.po.ap.ApIndent;
import com.company.po.indent.Indent;
import com.company.po.mgr.Manager;
import com.company.po.mgr.ManagerDeposit;
import com.company.dto.Order;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.mgr.dao.ManagerDepositDao;
import com.company.util.NumberUtil;

@Service(ManagerDepositService.BEAN_NAME)
public class ManagerDepositServiceImpl extends StringIdBaseServiceImpl<ManagerDeposit>//
        implements ManagerDepositService {
    
    @Autowired
    private SqlDao sqlDao;
    
    @Autowired
    private ManagerService managerService;

    @Autowired
    public void setBaseDao(ManagerDepositDao dao) {
        super.setBaseDao(dao);
    }

    private static BigDecimal MAX_DEPOSIT = new BigDecimal("10000");
    private static BigDecimal PER_DEPOSIT = new BigDecimal("0.05");
    
    
    @Override
    public List<ManagerDeposit> list(ManagerDeposit md, int start, int limit,
            List<Order> orders) {
        QueryInfo queryInfo = prepareQuery(md).order(orders).build();

        List<ManagerDeposit> _list = sqlDao.list(queryInfo.getSql(), start, limit, ManagerDeposit.class, queryInfo.getParArr());
        
        return _list;
    }

    private QueryInfoBuilder prepareQuery(ManagerDeposit md) {

        if (md == null) {
            md = new ManagerDeposit();
        }

        StringBuilder sql = new StringBuilder()//
                .append("select * from ZL_MANAGER_DEPOSIT")//
                .append(" where 1=1");


        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql.toString())//
                .andEq("MANAGER_ID", md.getManagerId())//
                .andEq("TRADE_TYPE", md.getTradeType())
                ;

        return builder;

    }

    @Override
    public BigDecimal calcDeposit(BigDecimal preAmt, BigDecimal tradeAmt) {
        // 保证金已经集满,本次交易无需再扣
        if (preAmt.compareTo(MAX_DEPOSIT) >= 0) {
            return BigDecimal.ZERO;
        }

        // 先按标准的百分比计算 保证金
        BigDecimal deposit = tradeAmt.multiply(PER_DEPOSIT)//
                .setScale(Constants.SCALE, RoundingMode.HALF_UP);

        // 之前已缴纳保证金 加上 本次计算所得，
        // 如果大于等于 缴纳上限，则 本次只需要缴纳 MAX - 之前已缴纳
        if (preAmt.add(deposit).compareTo(MAX_DEPOSIT) >= 0) {
            return MAX_DEPOSIT.subtract(preAmt);
        }

        return deposit;
    }

    @Override
    public void saveDeposit(Manager manager, ApBatch apBatch, ApIndent apIndent, BigDecimal depositAmt) {
        ManagerDeposit managerDeposit = new ManagerDeposit();
        managerDeposit.setManagerId(manager.getId());
        managerDeposit.setApBatchId(apIndent.getApBatchId());
        managerDeposit.setIndentId(apIndent.getIndentId());
        managerDeposit.setLineNo(apIndent.getLineNo());
        managerDeposit.setTradeType(EnumTradeType.EARNING.name());
        managerDeposit.setTradeDate(apBatch.getBatchDate());

        managerDeposit.setPreAmt(NumberUtil.getDecimal(manager.getDepositAmt()));
        managerDeposit.setCurAmt(depositAmt);
        managerDeposit.setDesc1("订单收入(订单号：" + apIndent.getIndentCode() + ")");

        // 期末余额
        BigDecimal balAmt = managerDeposit.getPreAmt().add(managerDeposit.getCurAmt());
        managerDeposit.setBalAmt(balAmt);

        manager.setDepositAmt(balAmt);

        super.save(managerDeposit);
    }
    
    
    @Override
    public void deductDeposit(BigDecimal deductDepositWorker, Manager manager, Indent indent) {
        ManagerDeposit managerDeposit = new ManagerDeposit();
        managerDeposit.setManagerId(manager.getId());
        managerDeposit.setTradeType(EnumTradeType.DEDUCT.name());
        managerDeposit.setTradeDate(new Date());
        managerDeposit.setPreAmt(NumberUtil.getDecimal(manager.getDepositAmt()));
        managerDeposit.setCurAmt(deductDepositWorker);
        managerDeposit.setDesc1("订单终止扣除(订单号：" + indent.getCode1() + ")");
        // 期末余额
        BigDecimal balAmt = managerDeposit.getPreAmt().subtract(managerDeposit.getCurAmt());
        managerDeposit.setBalAmt(balAmt);
        manager.setDepositAmt(balAmt);
        
        managerService.updateDepositAmt(manager);
        super.save(managerDeposit);
    }
}