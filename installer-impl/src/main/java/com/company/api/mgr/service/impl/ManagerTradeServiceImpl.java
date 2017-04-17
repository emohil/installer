package com.company.api.mgr.service.impl;

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
import com.company.api.mgr.service.ManagerDepositService;
import com.company.api.mgr.service.ManagerService;
import com.company.api.mgr.service.ManagerTradeService;
import com.company.api.sms.service.SmsService;
import com.company.api.sms.service.SmsTemplate;
import com.company.po.account.Account;
import com.company.po.ap.ApBatch;
import com.company.po.ap.ApIndent;
import com.company.po.bankcard.BankCard;
import com.company.po.mgr.Manager;
import com.company.po.mgr.ManagerTrade;
import com.company.sf.account.ManagerTradeSf;
import com.company.context.ThreadContext;
import com.company.dto.Order;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.mgr.dao.ManagerTradeDao;
import com.company.util.Dto;
import com.company.util.New;
import com.company.util.NumberUtil;
import com.company.util.StringUtil;

@Service(ManagerTradeService.BEAN_NAME)
public class ManagerTradeServiceImpl extends StringIdBaseServiceImpl<ManagerTrade>//
        implements ManagerTradeService {

    private static final Logger log = LoggerFactory.getLogger(ManagerTradeServiceImpl.class);

    @Autowired
    private SqlDao sqlDao;
    
    @Autowired
    private ManagerService managerService;
    
    @Autowired
    private AccountService accountService;

    @Autowired
    private ManagerDepositService managerDepositService;
    
    @Autowired
    private BankCardService bankCardService;
    
    @Autowired
    private SmsService smsService;
    

    @Autowired
    public void setBaseDao(ManagerTradeDao dao) {
        super.setBaseDao(dao);
    }
    
    @Override
    public int count(ManagerTradeSf sf) {
        QueryInfo queryInfo = prepareQuery(sf).build();
        return sqlDao.count(queryInfo.getCountSql(), queryInfo.getParArr());
    }

    @Override
    public List<ManagerTrade> list(ManagerTradeSf sf, int start, int limit, List<Order> orders) {
        QueryInfo queryInfo = prepareQuery(sf).order(orders).build();

        List<ManagerTrade> _list = sqlDao.list(queryInfo.getSql(), start, limit, ManagerTrade.class,
                queryInfo.getParArr());

        return _list;
    }

    private QueryInfoBuilder prepareQuery(ManagerTradeSf sf) {

        if (sf == null) {
            sf = new ManagerTradeSf();
        }

        StringBuilder sql = new StringBuilder()//
                .append("select managerTrade.*, account.NAME1 as 'MANAGER_NAME', bankCard.CARD_NO as 'CARD_NUM' from ZL_MANAGER_TRADE managerTrade")//
                .append(" left join ZL_ACCOUNT account on managerTrade.MANAGER_ID=account.MANAGER_ID")//
                .append(" left join ZL_MANAGER manager on managerTrade.MANAGER_ID=manager.ID")//
                .append(" left join ZL_BANK_CARD bankCard on managerTrade.MANAGER_ID=bankCard.OWNER_ID")//
                .append(" where 1=1");

        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql.toString())//
                .andEq("managerTrade.MANAGER_ID", sf.getManagerId())//
                .andEq("managerTrade.TRADE_TYPE", sf.getTradeType())//
                .andEq("managerTrade.TRADE_STATUS", sf.getTradeStatus())//
                .andContains("account.NAME1", sf.getAccountName1())//
                .andContains("manager.CODE1", sf.getAccountCode1());

        return builder;

    }

    @Override
    public void onApBatchPost(ApBatch apBatch, List<ApIndent> apIndentList) {

        Map<String, Manager> id2Mgr = New.hashMap();

        for (ApIndent apIndent : apIndentList) {

            String managerId = apIndent.getManagerId();

            Manager manager = id2Mgr.get(managerId);
            if (manager == null) {
                manager = managerService.find(managerId);
                id2Mgr.put(managerId, manager);
                if (manager == null) {
                    log.error("无效的经理人ID：{}", managerId);
                    throw new RuntimeException("无效的经理人ID：" + managerId);
                }
            }

            ManagerTrade managerTrade = new ManagerTrade();
            managerTrade.setManagerId(managerId);
            managerTrade.setApBatchId(apIndent.getApBatchId());
            managerTrade.setIndentId(apIndent.getIndentId());
            managerTrade.setLineNo(apIndent.getLineNo());
            managerTrade.setTradeType(EnumTradeType.EARNING.name());
            managerTrade.setTradeDate(apBatch.getBatchDate());
            managerTrade.setDesc1("");

            // 交易金额
            BigDecimal tradeAmt = apIndent.getManagerAmt();
            // 保证金额
            BigDecimal depositAmt = managerDepositService.calcDeposit(//
                    NumberUtil.getDecimal(manager.getDepositAmt()), tradeAmt);
            // 实际金额 = 交易金额 -保证金额
            BigDecimal curAmt = tradeAmt.subtract(depositAmt);
            // 期初金额
            managerTrade.setPreAmt(NumberUtil.getDecimal(manager.getBalAmt()));
            // 当期金额 = 实际金额
            managerTrade.setCurAmt(curAmt);
            // 期末余额
            BigDecimal balAmt = managerTrade.getPreAmt().add(managerTrade.getCurAmt());
            managerTrade.setBalAmt(balAmt);
            // 保证金额
            managerTrade.setDepositAmt(depositAmt);
            managerTrade.setDesc1("订单收入(订单号：" + apIndent.getIndentCode() + ")");
            // 期末余额
            manager.setBalAmt(balAmt);

            manager.setTotalProfit(NumberUtil.add(manager.getTotalProfit(), tradeAmt));
            super.save(managerTrade);

            // 需要缴纳保证金
            if (depositAmt.compareTo(BigDecimal.ZERO) == 1) {
                managerDepositService.saveDeposit(manager, apBatch, apIndent, depositAmt);
            }
        }
    }

    @Override
    public ManagerTrade findRecentlyTrade(String managerId) {
        String sql = new StringBuilder()//
                .append("select * from ZL_MANAGER_TRADE")//
                .append(" where MANAGER_ID=?")//
                .append(" order by CREATE_DATE desc, LINE_NO desc")//
                .toString();

        List<ManagerTrade> list = sqlDao.list(sql, 1, 1, ManagerTrade.class, new Object[] { managerId });

        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public Dto cashTrade(String managerId, BigDecimal tradeAmt) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        // 检查提现金额的正确性
        if (tradeAmt.compareTo(BigDecimal.ZERO) <= 0) {
            ret.put(TAG_CODE, EnumErrorCode.TRADE_1001.name());
            ret.put(TAG_MSG, EnumErrorCode.TRADE_1001.text());
            return ret;
        }
        Manager manager = managerService.find(managerId);
        if (manager == null) {
            ret.put(TAG_MSG, "经理人ID参数无效！");
            return ret;
        }
        // 判断余额是否充足
        BigDecimal balAmt = NumberUtil.getDecimal(manager.getBalAmt());
        if (balAmt.compareTo(tradeAmt) < 0) {
            ret.put(TAG_CODE, EnumErrorCode.TRADE_1002.name());
            ret.put(TAG_MSG, EnumErrorCode.TRADE_1002.text());
            return ret;
        }

        // 检查余额 与 交易流水最后一条金额 是否一致
        ManagerTrade recentTrade = this.findRecentlyTrade(managerId);
        if (recentTrade == null || balAmt.compareTo(recentTrade.getBalAmt()) != 0) {
            ret.put(TAG_CODE, EnumErrorCode.TRADE_1003.name());
            ret.put(TAG_MSG, EnumErrorCode.TRADE_1003.text());
            return ret;
        }

        BigDecimal cashBalAmt = balAmt.subtract(tradeAmt);
        manager.setBalAmt(cashBalAmt);

        ManagerTrade curtrade = new ManagerTrade();
        curtrade.setManagerId(managerId);
        curtrade.setPreAmt(balAmt);
        curtrade.setCurAmt(tradeAmt);
        curtrade.setBalAmt(cashBalAmt);
        curtrade.setTradeType(EnumTradeType.CASH.name());
        curtrade.setTradeType(EnumTradeStatus.TREATMENT.name());
        curtrade.setTradeDate(new Date());
        curtrade.setDesc1("提现（处理中）");

        super.save(curtrade);

        ret.put(TAG_MSG, "提现操作成功！");
        ret.put(TAG_SUCCESS, true);
        {
            Map<String, String> extras = new HashMap<String, String>();
            extras.put("id", "");
            extras.put("accountType", account.getLastLoginType());
            extras.put("pushType", EnumJpushType.BALANCE_DETAIL.name());
            List<String> registrationIds = new ArrayList<String>();
            if (StringUtil.isNotBlank(account.getRegistrationId())) {
                registrationIds.add(account.getRegistrationId());
            }
            if (registrationIds.size() > 0) {
                MessagePush push = new MessagePush("提现申请已提交，金额￥" + tradeAmt + "，7日内到账", "众联工匠", extras);
                push.pushMsgToRegistrationIds(registrationIds);
            }
        }
        
        return ret;
    }

    @Override
    public BigDecimal monthEarning(String managerId) {
        String sql = "select sum(DEPOSIT_AMT + CUR_AMT) from ZL_MANAGER_TRADE where TRADE_TYPE=? and MANAGER_ID=? and date_format(CREATE_DATE,'%Y-%m')=date_format(now(),'%Y-%m')";
        return sqlDao.unique(sql, (ResultTransformer) null, EnumTradeType.EARNING.name(), managerId);
    }

    @Override
    public BigDecimal totalTradeAmt() {
        String sql = "select sum(DEPOSIT_AMT + CUR_AMT) from ZL_MANAGER_TRADE where TRADE_TYPE=?";
        BigDecimal amt = sqlDao.unique(sql, (ResultTransformer) null, EnumTradeType.EARNING.name());
        if (amt == null) {
            amt = BigDecimal.ZERO;
        }
        return amt;
    }
    
    @Override
    public void doUpdate(ManagerTrade data, Manager manager, ManagerTrade managerTrade) {
        update(data);
        managerService.update(manager);
        save(managerTrade);
        Account account = accountService.findByManagerId(data.getManagerId());
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
                push = new MessagePush("提现成功（金额￥"+data.getCurAmt()+"）", "众联工匠", extras);
                push.pushMsgToRegistrationIds(registrationIds);
                BankCard bankCard = bankCardService.findByOwnerId(manager.getId());
                String cardNo = bankCard.getCardNo();
                String minCardNo = (String) cardNo.subSequence(cardNo.length()-4, cardNo.length());
                smsService.sendSms(account.getMobile(), SmsTemplate.DEPOSIT_RESULT, data.getCurAmt(), bankCard.getBankName(), minCardNo);
            }
            if (data.getTradeStatus().equals(EnumTradeStatus.FAILURE.name())) {
                push = new MessagePush("提现失败（金额￥"+data.getCurAmt()+"）", "众联工匠", extras);
                push.pushMsgToRegistrationIds(registrationIds);
            }
        }
    }
    
    @Override
    public int monthDrawingsCount(String managerId) {
        String sql = "select count(*) from ZL_MANAGER_TRADE where MANAGER_ID=? and date_format(CREATE_DATE,'%Y-%m')=date_format(now(),'%Y-%m') and TRADE_TYPE=? and TRADE_STATUS=?";
        return sqlDao.count(sql, managerId, EnumTradeType.CASH.name(), EnumTradeStatus.ACCOMPLISH.name());
    }
}