package com.company.api.wk.service.impl;

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
import com.company.api.wk.service.WorkerDepositService;
import com.company.api.wk.service.WorkerService;
import com.company.po.ap.ApBatch;
import com.company.po.ap.ApIndent;
import com.company.po.indent.Indent;
import com.company.po.wk.Worker;
import com.company.po.wk.WorkerDeposit;
import com.company.dto.Order;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.wk.dao.WorkerDepositDao;
import com.company.util.NumberUtil;

@Service(WorkerDepositService.BEAN_NAME)
public class WorkerDepositServiceImpl extends StringIdBaseServiceImpl<WorkerDeposit>//
implements WorkerDepositService {

    private static BigDecimal MAX_DEPOSIT = new BigDecimal("5000");
    private static BigDecimal PER_DEPOSIT = new BigDecimal("0.05");
    
    @Autowired
    private SqlDao sqlDao;
    
    @Autowired
    private WorkerService workerService;
    

    @Autowired
    public void setBaseDao(WorkerDepositDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public List<WorkerDeposit> list(WorkerDeposit wd, int start, int limit,
            List<Order> orders) {
        QueryInfo queryInfo = prepareQuery(wd).order(orders).build();

        List<WorkerDeposit> _list = sqlDao.list(queryInfo.getSql(), start, limit, WorkerDeposit.class, queryInfo.getParArr());
        
        return _list;
    }

    private QueryInfoBuilder prepareQuery(WorkerDeposit wd) {

        if (wd == null) {
            wd = new WorkerDeposit();
        }

        StringBuilder sql = new StringBuilder()//
                .append("select * from ZL_WORKER_DEPOSIT")//
                .append(" where 1=1");


        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql.toString())//
                .andEq("WORKER_ID", wd.getWorkerId())//
                .andEq("TRADE_TYPE", wd.getTradeType())
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
    public void saveDeposit(Worker worker, ApBatch apBatch, ApIndent apIndent, BigDecimal depositAmt) {
        WorkerDeposit workerDeposit = new WorkerDeposit();
        workerDeposit.setWorkerId(worker.getId());
        workerDeposit.setApBatchId(apIndent.getApBatchId());
        workerDeposit.setIndentId(apIndent.getIndentId());
        workerDeposit.setLineNo(apIndent.getLineNo());
        workerDeposit.setTradeType(EnumTradeType.EARNING.name());
        workerDeposit.setTradeDate(apBatch.getBatchDate());

        workerDeposit.setPreAmt(NumberUtil.getDecimal(worker.getDepositAmt()));
        workerDeposit.setCurAmt(depositAmt);
        workerDeposit.setDesc1("订单收入(订单号：" + apIndent.getIndentCode() + ")");

        // 期末余额
        BigDecimal balAmt = workerDeposit.getPreAmt().add(workerDeposit.getCurAmt());
        workerDeposit.setBalAmt(balAmt);
        
        worker.setDepositAmt(balAmt);

        super.save(workerDeposit);
    }
    
    @Override
    public void deductDeposit(BigDecimal deductDepositWorker, Worker worker, Indent indent) {
        //扣除保证金
        
        WorkerDeposit workerDeposit = new WorkerDeposit();
        workerDeposit.setWorkerId(worker.getId());
        workerDeposit.setTradeType(EnumTradeType.DEDUCT.name());
        workerDeposit.setTradeDate(new Date());
        workerDeposit.setPreAmt(NumberUtil.getDecimal(worker.getDepositAmt()));
        workerDeposit.setCurAmt(deductDepositWorker);
        workerDeposit.setDesc1("订单终止扣除(订单号：" + indent.getCode1() + ")");
        // 期末余额
        BigDecimal balAmt = workerDeposit.getPreAmt().subtract(workerDeposit.getCurAmt());
        workerDeposit.setBalAmt(balAmt);
        worker.setDepositAmt(balAmt);
        workerService.updateDepositAmt(worker);

        super.save(workerDeposit);
    }
}