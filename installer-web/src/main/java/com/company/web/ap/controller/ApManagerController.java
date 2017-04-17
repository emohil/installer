package com.company.web.ap.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.api.ap.service.ApManagerService;
import com.company.po.ap.ApManager;
import com.company.web.BasePagerController;
import com.company.dto.Order;

@Controller("webApManagerController")
@RequestMapping(value = "/apManager")
public class ApManagerController extends BasePagerController<ApManager, ApManager> {

    private static final String PATH = "ap/apManager";

    @Autowired
    private ApManagerService service;

    @Override
    protected int doCount(ApManager searchForm) throws Exception {
        return service.count(searchForm);
    }

    @Override
    protected List<?> doList(ApManager searchForm, int start, int limit, List<Order> orders) throws Exception {
        return service.list(searchForm, start, limit, orders);
    }

    @RequestMapping(value = "apManagerList")
    protected String apartyList(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "apBatchId", required = true) String apBatchId) {
        
        model.addAttribute("apBatchId", apBatchId);
        
        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/apManagerList";
    }
}