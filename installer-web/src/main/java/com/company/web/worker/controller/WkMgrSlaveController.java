package com.company.web.worker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.api.wk.service.WkMgrSlaveService;
import com.company.po.wk.WkMgrSlave;
import com.company.web.BasePagerController;

@Controller("webWkMgrSlaveController")
@RequestMapping(value = "/wkMgrSlave")
public class WkMgrSlaveController extends BasePagerController<WkMgrSlave, WkMgrSlave> {

    private static final String PATH = "wkMgrSlave";

    @Autowired
    private WkMgrSlaveService service;
    
    @RequestMapping(value = "/managerList")
    public String findMgrListByWorkerId(@RequestParam("workerId") String workerId,
            ModelMap model) {
        model.put(TAG_ID, workerId);
        return PATH + "/wkManagerlist";
    }
    

}
