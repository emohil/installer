package com.company.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.api.account.service.EnumCheckStatus;
import com.company.api.aparty.service.ApartyService;
import com.company.api.indent.service.EnumExecuteStatus;
import com.company.api.indent.service.IndentService;
import com.company.api.item.service.ItemService;
import com.company.api.mgr.service.ManagerService;
import com.company.api.mgr.service.ManagerTradeService;
import com.company.api.wk.service.WorkerService;
import com.company.api.wk.service.WorkerTradeService;
import com.company.web.BaseController;
import com.company.dto.Filter;
import com.company.util.NumberUtil;

@Controller("webHomepageController")
@RequestMapping
public class HomepageController extends BaseController {

    @Autowired
    private ApartyService apartyService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private IndentService indentService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private WorkerService workerService;
    
    @Autowired
    private ManagerTradeService managerTradeService;
    
    @Autowired
    private WorkerTradeService workerTradeService;

    @RequestMapping(value = "/homepage")
    public String homepage(ModelMap model) {

        // 甲方相关
        model.put("totalAparty", apartyService.count());
        model.put("totalItem", itemService.count());

        // 订单相关
        model.put("totalIndent", indentService.count());

        Map<EnumExecuteStatus, Integer> indentExeStatus2Count = indentService.executeStatus2Count();
        model.put("indentAfter", NumberUtil.toInt(indentExeStatus2Count.get(EnumExecuteStatus.AFTER), 0));
        model.put("indentCentre", NumberUtil.toInt(indentExeStatus2Count.get(EnumExecuteStatus.CENTRE), 0));
        model.put("indentBefore", NumberUtil.toInt(indentExeStatus2Count.get(EnumExecuteStatus.BEFORE), 0));

        // 经理人相关
        model.put("totalManager", managerService.count());
        model.put("totalManagerTrade", managerTradeService.totalTradeAmt());
        model.put("afteritems", itemService.count(Filter.eq("executeStatus", EnumExecuteStatus.AFTER.name())));

        // 工人相关
        model.put("totalWorker", workerService.count());
        model.put("totalWorkerTrade", workerTradeService.totalTradeAmt());
        model.put("notpassWorkers", workerService.count(Filter.eq("status", EnumCheckStatus.NOTPASS.name())));

        return "homepage";
    }
    
    
    @RequestMapping(value = "/noAuth")
    public String noAuth(ModelMap model) {
        
        return "noAuth";
    }
    
}
