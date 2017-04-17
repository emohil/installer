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
import com.company.api.dict.service.EnumTradeType;
import com.company.api.fw.EnumCodes;
import com.company.api.mgr.service.ManagerService;
import com.company.api.mgr.service.ManagerTradeService;
import com.company.po.account.Account;
import com.company.po.admin.Admin;
import com.company.po.bankcard.BankCard;
import com.company.po.mgr.Manager;
import com.company.po.mgr.ManagerTrade;
import com.company.sf.account.ManagerTradeSf;
import com.company.web.BasePagerController;
import com.company.api.fw.service.SysDictService;
import com.company.context.ThreadContext;
import com.company.dto.Order;
import com.company.dto.SysDict;
import com.company.util.Dto;

@Controller("webManagerTradeController")
@RequestMapping(value = "/managerTrade")
public class ManagerTradeController extends BasePagerController<ManagerTradeSf, ManagerTrade> {

    private static final String PATH = "manager";
    
    @Autowired
    private ManagerTradeService service;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private ManagerService managerService;

    @Autowired
    private SysDictService sysDictService;
    
    @Autowired
    private BankCardService bankCardService;
    
    @Autowired
    public void setBaseService(ManagerTradeService service) {
        super.setBaseService(service);
        this.service = service;
    }

    @Override
    protected int doCount(ManagerTradeSf sf) throws Exception {
        return service.count(sf);
    }

    @Override
    protected List<?> doList(ManagerTradeSf sf, int start, int limit,
            List<Order> orders) throws Exception {
        return service.list(sf, start, limit, orders);
    }
    
    @RequestMapping(value = "/managerTradeList")
    protected String managerTradeList(ModelMap model) {
        model.put("tradeStatusList", sysDictService.listGroupCopy(EnumCodes.TRADE_STATUS, SysDict.CHECK));
        return PATH + "/managerTradeList";
    }
    
    @RequestMapping(value = "/managerTradeEdit")
    public String workerTradeEdit(@RequestParam(value = "id") String id,
            @RequestParam(value = "status") String status,
            ModelMap model) {
        model.put(TAG_ID, id);
        model.put("tradeStatusList", sysDictService.listGroupCopy(EnumCodes.TRADE_STATUS, SysDict.CHECK));
        if (status.equals("0")) {
            return PATH + "/managerTradeEdit";
        } else {
            return PATH + "/managerTradeMsg";
        }
    }
    
    @RequestMapping(value = "/findManagerTrade")
    @ResponseBody
    public ManagerTrade findManagerTrade(@RequestParam("id") String id) {
        ManagerTrade managerTrade = service.find(id);
        Account account = accountService.findByManagerId(managerTrade.getManagerId());
        //统计本月提现次数
        int monthDrawingsCount = service.monthDrawingsCount(managerTrade.getManagerId());
        if (monthDrawingsCount < 4) {
            managerTrade.setFactorage(BigDecimal.ZERO);
            managerTrade.setActualAmt(managerTrade.getCurAmt());
        } else {
            BigDecimal factorage = managerTrade.getCurAmt().multiply(BigDecimal.valueOf(0.01)).setScale(2, RoundingMode.HALF_UP);
            managerTrade.setFactorage(factorage);
            managerTrade.setActualAmt(managerTrade.getCurAmt().subtract(factorage));
        }
        
        
        managerTrade.setManagerName(account.getName1());
        BankCard bankCard = bankCardService.findByOwnerId(managerTrade.getManagerId());
        managerTrade.setBankCard(bankCard);
        return managerTrade;
    }
    
    @RequestMapping(value = "/doUpdate")
    @ResponseBody
    public Dto doUpdate(@RequestBody ManagerTrade data) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);
        Admin admin = (Admin) ThreadContext.getContextBean();
        data.setModifyBy(admin.getName1());
        Manager manager = managerService.find(data.getManagerId());
        ManagerTrade managerTrade = new ManagerTrade();
        if (data.getTradeStatus().equals("FAILURE")) {
            managerTrade.setManagerId(data.getManagerId());
            managerTrade.setTradeType(EnumTradeType.EARNING.name());
            managerTrade.setDesc1(data.getDesc1());
            managerTrade.setPreAmt(manager.getBalAmt());
            managerTrade.setCurAmt(data.getCurAmt());
            managerTrade.setBalAmt(manager.getBalAmt().add(data.getCurAmt()));
            manager.setBalAmt(manager.getBalAmt().add(data.getCurAmt()));
            managerTrade.setLineNo(1);
            managerTrade.setCode1(data.getCode1());
            managerTrade.setTradeDate(new Date());
        }
        service.doUpdate(data, manager, managerTrade);
        ret.put(TAG_SUCCESS, true);
        return ret;
    }

}
