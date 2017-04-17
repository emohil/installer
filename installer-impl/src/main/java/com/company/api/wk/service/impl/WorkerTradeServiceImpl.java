package com.company.api.wk.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.account.service.AccountService;
import com.company.api.bankcard.service.BankCardService;
import com.company.api.dict.service.EnumTradeStatus;
import com.company.api.dict.service.EnumTradeType;
import com.company.api.fw.service.EnumErrorCode;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.fw.service.impl.query.QueryInfo;
import com.company.api.fw.service.impl.query.QueryInfoBuilder;
import com.company.api.fw.util.MessagePush;
import com.company.api.indent.service.EnumJpushType;
import com.company.api.sms.service.SmsService;
import com.company.api.sms.service.SmsTemplate;
import com.company.api.wk.service.WorkerDepositService;
import com.company.api.wk.service.WorkerService;
import com.company.api.wk.service.WorkerTradeService;
import com.company.po.account.Account;
import com.company.po.ap.ApBatch;
import com.company.po.ap.ApIndent;
import com.company.po.bankcard.BankCard;
import com.company.po.wk.Worker;
import com.company.po.wk.WorkerTrade;
import com.company.sf.account.WorkerTradeSf;
import com.company.context.ThreadContext;
import com.company.dto.Order;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.wk.dao.WorkerTradeDao;
import com.company.util.Dto;
import com.company.util.New;
import com.company.util.NumberUtil;
import com.company.util.StringUtil;

@Service(WorkerTradeService.BEAN_NAME)
public class WorkerTradeServiceImpl extends StringIdBaseServiceImpl<WorkerTrade>//
        implements WorkerTradeService {

    private static final Logger log = LoggerFactory.getLogger(WorkerTradeServiceImpl.class);

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    private WorkerService workerService;
    
    @Autowired
    private AccountService accountService;

    @Autowired
    private WorkerDepositService workerDepositService;
    
    @Autowired
    private BankCardService bankCardService;

    @Autowired
    private SmsService smsService;
    @Autowired
    public void setBaseDao(WorkerTradeDao dao) {
        super.setBaseDao(dao);
    }
    
    @Override
    public int count(WorkerTradeSf sf) {
        QueryInfo queryInfo = prepareQuery(sf).build();
        return sqlDao.count(queryInfo.getCountSql(), queryInfo.getParArr());
    }

    @Override
    public List<WorkerTrade> list(WorkerTradeSf sf, int start, int limit, List<Order> orders) {
        QueryInfo queryInfo = prepareQuery(sf).order(orders).build();

        List<WorkerTrade> _list = sqlDao.list(queryInfo.getSql(), start, limit, WorkerTrade.class,
                queryInfo.getParArr());

        return _list;
    }

    private QueryInfoBuilder prepareQuery(WorkerTradeSf sf) {

        if (sf == null) {
            sf = new WorkerTradeSf();
        }

        StringBuilder sql = new StringBuilder()//
                .append("select workerTrade.*, account.NAME1 as 'WORKER_NAME', bankCard.CARD_NO as 'CARD_NUM' from ZL_WORKER_TRADE workerTrade")//
                .append(" left join ZL_ACCOUNT account on workerTrade.WORKER_ID=account.WORKER_ID")//
                .append(" left join ZL_WORKER worker on workerTrade.WORKER_ID=worker.ID")//
                .append(" left join ZL_BANK_CARD bankCard on workerTrade.WORKER_ID=bankCard.OWNER_ID")//
                .append(" where 1=1");

        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql.toString())//
                .andEq("workerTrade.WORKER_ID", sf.getWorkerId())//
                .andEq("workerTrade.TRADE_TYPE", sf.getTradeType())//
                .andEq("workerTrade.TRADE_STATUS", sf.getTradeStatus())//
                .andContains("account.NAME1", sf.getAccountName1())//
                .andContains("worker.CODE1", sf.getAccountCode1());

        return builder;

    }

    @Override
    public void onApBatchPost(ApBatch apBatch, List<ApIndent> apIndentList) {

        Map<String, Worker> id2Mgr = New.hashMap();

        for (ApIndent apIndent : apIndentList) {

            String workerId = apIndent.getWorkerId();

            Worker worker = id2Mgr.get(workerId);
            if (worker == null) {
                worker = workerService.find(workerId);
                id2Mgr.put(workerId, worker);
                if (worker == null) {
                    log.error("无效的经理人ID：{}", workerId);
                    throw new RuntimeException("无效的经理人ID：" + workerId);
                }
            }

            WorkerTrade workerTrade = new WorkerTrade();
            workerTrade.setWorkerId(workerId);
            workerTrade.setApBatchId(apIndent.getApBatchId());
            workerTrade.setIndentId(apIndent.getIndentId());
            workerTrade.setLineNo(apIndent.getLineNo());
            workerTrade.setTradeType(EnumTradeType.EARNING.name());
            workerTrade.setTradeDate(apBatch.getBatchDate());
            workerTrade.setDesc1("");

            // 交易金额
            BigDecimal tradeAmt = apIndent.getWorkerAmt();
            // 保证金额
            BigDecimal depositAmt = workerDepositService.calcDeposit(//
                    NumberUtil.getDecimal(worker.getDepositAmt()), tradeAmt);
            // 实际金额 = 交易金额 -保证金额
            BigDecimal curAmt = tradeAmt.subtract(depositAmt);
            // 期初金额
            workerTrade.setPreAmt(NumberUtil.getDecimal(worker.getBalAmt()));
            // 当期金额 = 实际金额
            workerTrade.setCurAmt(curAmt);
            // 期末余额
            BigDecimal balAmt = workerTrade.getPreAmt().add(workerTrade.getCurAmt());
            workerTrade.setBalAmt(balAmt);
            // 保证金额
            workerTrade.setDepositAmt(depositAmt);
            workerTrade.setDesc1("订单收入(订单号：" + apIndent.getIndentCode() + ")");
            // 期末余额
            worker.setBalAmt(balAmt);

            worker.setTotalProfit(NumberUtil.add(worker.getTotalProfit(), tradeAmt));

            super.save(workerTrade);

            // 需要缴纳保证金
            if (depositAmt.compareTo(BigDecimal.ZERO) == 1) {
                workerDepositService.saveDeposit(worker, apBatch, apIndent, depositAmt);
            }
        }
    }
    
    @Override
    public BigDecimal monthEarning(String workerId) {
        String sql = "select sum(DEPOSIT_AMT + CUR_AMT) from ZL_WORKER_TRADE where TRADE_TYPE=? and WORKER_ID=? and date_format(CREATE_DATE,'%Y-%m')=date_format(now(),'%Y-%m')";
        return sqlDao.unique(sql, (ResultTransformer) null, EnumTradeType.EARNING.name(), workerId);
    }

    @Override
    public WorkerTrade findRecentlyTrade(String workerId) {
        String sql = new StringBuilder()//
                .append("select * from ZL_WORKER_TRADE")//
                .append(" where WORKER_ID=?")//
                .append(" order by CREATE_DATE desc, LINE_NO desc").toString();

        List<WorkerTrade> list = sqlDao.list(sql, 1, 1, WorkerTrade.class, new Object[] { workerId });

        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public Dto cashTrade(String workerId, BigDecimal tradeAmt) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);

        Account account = (Account) ThreadContext.getContextBean();
        // 检查提现金额的正确性
        if (tradeAmt.compareTo(BigDecimal.ZERO) <= 0) {
            ret.put(TAG_CODE, EnumErrorCode.TRADE_1001.name());
            ret.put(TAG_MSG, EnumErrorCode.TRADE_1001.text());
            return ret;
        }
        Worker worker = workerService.find(workerId);
        if (worker == null) {
            ret.put(TAG_MSG, "工人ID参数无效！");
            return ret;
        }
        // 判断余额是否充足
        BigDecimal balAmt = NumberUtil.getDecimal(worker.getBalAmt());
        if (balAmt.compareTo(tradeAmt) < 0) {
            ret.put(TAG_CODE, EnumErrorCode.TRADE_1002.name());
            ret.put(TAG_MSG, EnumErrorCode.TRADE_1002.text());
            return ret;
        }

        // 检查余额 与 交易流水最后一条金额 是否一致
        WorkerTrade recentTrade = this.findRecentlyTrade(workerId);
        if (recentTrade == null || balAmt.compareTo(recentTrade.getBalAmt()) != 0) {
            ret.put(TAG_CODE, EnumErrorCode.TRADE_1003.name());
            ret.put(TAG_MSG, EnumErrorCode.TRADE_1003.text());
            return ret;
        }

        BigDecimal cashBalAmt = balAmt.subtract(tradeAmt);
        worker.setBalAmt(cashBalAmt);

        WorkerTrade curtrade = new WorkerTrade();
        curtrade.setWorkerId(workerId);
        curtrade.setPreAmt(balAmt);
        curtrade.setCurAmt(tradeAmt);
        curtrade.setBalAmt(cashBalAmt);
        curtrade.setTradeType(EnumTradeType.CASH.name());
        curtrade.setTradeStatus(EnumTradeStatus.TREATMENT.name());
        curtrade.setTradeDate(new Date());
        curtrade.setDesc1("提现（处理中）");

        super.save(curtrade);

        ret.put(TAG_MSG, "提现操作成功！");
        ret.put(TAG_SUCCESS, true);
        {
            Map<String, String> extras = new HashMap<String, String>();
            extras.put("id", "");
            extras.put("pushType", EnumJpushType.BALANCE_DETAIL.name());
            List<String> registrationIds = new ArrayList<String>();
            if (StringUtil.isNotBlank(account.getRegistrationId())) {
                registrationIds.add(account.getRegistrationId());
            }
            MessagePush push = new MessagePush("提现申请已提交，金额￥" + tradeAmt + "，7日内到账", "众联工匠", extras);
            if (registrationIds.size() > 0) {
                push.pushMsgToRegistrationIds(registrationIds);
            }
        }
        
        return ret;
    }

    @Override
    public BigDecimal totalTradeAmt() {
        String sql = "select sum(DEPOSIT_AMT + CUR_AMT) from ZL_WORKER_TRADE where TRADE_TYPE=?";
        BigDecimal amt = sqlDao.unique(sql, (ResultTransformer) null, EnumTradeType.EARNING.name());
        if (amt == null) {
            amt = BigDecimal.ZERO;
        }
        return amt;
    }
    
    public void doUpdate(WorkerTrade data, Worker worker, WorkerTrade workerTrade) {
        update(data);
        workerService.update(worker);
        save(workerTrade);
        Account account = accountService.findByWorkerId(data.getWorkerId());
        {
            Map<String, String> extras = new HashMap<String, String>();
            extras.put("id", "");
            extras.put("pushType", EnumJpushType.BALANCE_DETAIL.name());
            List<String> registrationIds = new ArrayList<String>();
            if (StringUtil.isNotBlank(account.getRegistrationId())) {
                registrationIds.add(account.getRegistrationId());
            }
            MessagePush push = null;
            if (data.getTradeStatus().equals(EnumTradeStatus.ACCOMPLISH.name())) {
                if (registrationIds.size() > 0) {
                    push = new MessagePush("提现成功（金额￥"+data.getCurAmt()+"）", "众联工匠", extras);
                    push.pushMsgToRegistrationIds(registrationIds);
                }
                BankCard bankCard = bankCardService.findByOwnerId(worker.getId());
                String cardNo = bankCard.getCardNo();
                String minCardNo = (String) cardNo.subSequence(cardNo.length()-4, cardNo.length());
                smsService.sendSms(account.getMobile(), SmsTemplate.DEPOSIT_RESULT, data.getCurAmt(), bankCard.getBankName(), minCardNo);

            }
            if (data.getTradeStatus().equals(EnumTradeStatus.FAILURE.name())) {
                if (registrationIds.size() > 0) {
                    push = new MessagePush("提现失败（金额￥"+data.getCurAmt()+"）", "众联工匠", extras);
                    push.pushMsgToRegistrationIds(registrationIds);
                }
            }
        }
    }
    
    @Override
    public int monthDrawingsCount(String workerId) {
        String sql = "select count(*) from ZL_WORKER_TRADE where WORKER_ID=? and date_format(CREATE_DATE,'%Y-%m')=date_format(now(),'%Y-%m') and TRADE_TYPE=? and TRADE_STATUS=?";
        return sqlDao.count(sql, workerId, EnumTradeType.CASH.name(), EnumTradeStatus.ACCOMPLISH.name());
    }

}