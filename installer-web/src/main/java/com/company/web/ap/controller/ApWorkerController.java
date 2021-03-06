package com.company.web.ap.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.api.ap.service.ApWorkerService;
import com.company.po.ap.ApWorker;
import com.company.web.BasePagerController;
import com.company.dto.Order;

@Controller("webApWorkerController")
@RequestMapping(value = "/apWorker")
public class ApWorkerController extends BasePagerController<ApWorker, ApWorker> {

    private static final String PATH = "ap/apWorker";

    @Autowired
    private ApWorkerService service;

    @Override
    protected int doCount(ApWorker searchForm) throws Exception {
        return service.count(searchForm);
    }

    @Override
    protected List<?> doList(ApWorker searchForm, int start, int limit, List<Order> orders) throws Exception {
        return service.list(searchForm, start, limit, orders);
    }

    @RequestMapping(value = "apWorkerList")
    protected String apartyList(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "apBatchId", required = true) String apBatchId,
            @RequestParam(value = "managerId", required = false) String managerId) {
        
        model.addAttribute("apBatchId", apBatchId);
        model.addAttribute("managerId", managerId);
        
        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/apWorkerList";
    }
}