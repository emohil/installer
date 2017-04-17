package com.company.web.trade.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.account.service.AccountService;
import com.company.api.bankcard.service.BankCardService;
import com.company.api.dict.service.EnumTradeStatus;
import com.company.api.dict.service.EnumTradeType;
import com.company.api.fw.EnumCodes;
import com.company.api.wk.service.WorkerService;
import com.company.api.wk.service.WorkerTradeService;
import com.company.po.account.Account;
import com.company.po.admin.Admin;
import com.company.po.bankcard.BankCard;
import com.company.po.wk.Worker;
import com.company.po.wk.WorkerTrade;
import com.company.sf.account.WorkerTradeSf;
import com.company.web.BasePagerController;
import com.company.api.fw.service.SysDictService;
import com.company.context.ThreadContext;
import com.company.dto.Order;
import com.company.dto.SysDict;
import com.company.util.Dto;

@Controller("webWorkerTradeController")
@RequestMapping(value = "/workerTrade")
public class WorkerTradeController extends BasePagerController<WorkerTradeSf, WorkerTrade> {

    private static final String PATH = "worker";
    
    @Autowired
    private WorkerTradeService service;
    
    @Autowired
    private WorkerService workerService;
    
    @Autowired
    private AccountService accountService;

    @Autowired
    private SysDictService sysDictService;
    
    @Autowired
    private BankCardService bankCardService;
    
    @Autowired
    public void setBaseService(WorkerTradeService service) {
        super.setBaseService(service);
        this.service = service;
    }

    @Override
    protected int doCount(WorkerTradeSf sf) throws Exception {
        return service.count(sf);
    }

    @Override
    protected List<?> doList(WorkerTradeSf sf, int start, int limit,
            List<Order> orders) throws Exception {
        return service.list(sf, start, limit, orders);
    }
    
    @RequestMapping(value = "/workerTradeList")
    protected String workerTradeList(ModelMap model) {
        model.put("tradeStatusList", sysDictService.listGroupCopy(EnumCodes.TRADE_STATUS, SysDict.CHECK));
        return PATH + "/workerTradeList";
    }
    
    @RequestMapping(value = "/workerTradeEdit")
    public String workerTradeEdit(@RequestParam(value = "id") String id,
            @RequestParam(value = "status") String status,
            ModelMap model) {
        model.put(TAG_ID, id);
        model.put("tradeStatusList", sysDictService.listGroupCopy(EnumCodes.TRADE_STATUS, SysDict.CHECK));
        if (status.equals("0")) {
            return PATH + "/workerTradeEdit";
        } else {
            return PATH + "/workerTradeMsg";
        }
    }
    
    @RequestMapping(value = "/findWorkerTrade")
    @ResponseBody
    public WorkerTrade findWorkerTrade(@RequestParam("id") String id) {
        WorkerTrade workerTrade = service.find(id);
        Account account = accountService.findByWorkerId(workerTrade.getWorkerId());
        //统计本月提现次数
        int monthDrawingsCount = service.monthDrawingsCount(workerTrade.getWorkerId());
        if (monthDrawingsCount < 4) {
            workerTrade.setFactorage(BigDecimal.ZERO);
            workerTrade.setActualAmt(workerTrade.getCurAmt());
        } else {
            BigDecimal factorage = workerTrade.getCurAmt().multiply(BigDecimal.valueOf(0.01)).setScale(2, RoundingMode.HALF_UP);
            workerTrade.setFactorage(factorage);
            workerTrade.setActualAmt(workerTrade.getCurAmt().subtract(factorage));
        }
        workerTrade.setWorkerName(account.getName1());
        BankCard bankCard = bankCardService.findByOwnerId(workerTrade.getWorkerId());
        workerTrade.setBankCard(bankCard);
        return workerTrade;
    }
    
    @RequestMapping(value = "/doUpdate")
    @ResponseBody
    public Dto doUpdate(@RequestBody WorkerTrade data) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);
        Admin admin = (Admin) ThreadContext.getContextBean();
        data.setModifyBy(admin.getName1());
        Worker worker = workerService.find(data.getWorkerId());
        WorkerTrade workerTrade = new WorkerTrade();
        if (data.getTradeStatus().equals(EnumTradeStatus.FAILURE.name())) {
            workerTrade.setWorkerId(data.getWorkerId());
            workerTrade.setTradeType(EnumTradeType.REFUND.name());
            workerTrade.setDesc1(data.getDesc1());
            workerTrade.setPreAmt(worker.getBalAmt());
            workerTrade.setCurAmt(data.getCurAmt());
            workerTrade.setBalAmt(worker.getBalAmt().add(data.getCurAmt()));
            worker.setBalAmt(worker.getBalAmt().add(data.getCurAmt()));
            workerTrade.setLineNo(1);
            workerTrade.setCode1(data.getCode1());
            workerTrade.setTradeDate(new Date());
        }
        service.doUpdate(data, worker, workerTrade);
        ret.put(TAG_SUCCESS, true);
        return ret;
    }

}
