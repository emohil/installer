package com.company.web.ap.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.api.ap.service.ApIndentService;
import com.company.po.ap.ApIndent;
import com.company.web.BasePagerController;
import com.company.dto.Order;

@Controller("webApIndentController")
@RequestMapping(value = "/apIndent")
public class ApIndentController extends BasePagerController<ApIndent, ApIndent> {

    private static final String PATH = "ap/apIndent";

    @Autowired
    private ApIndentService service;

    @Override
    protected int doCount(ApIndent searchForm) throws Exception {
        return service.count(searchForm);
    }

    @Override
    protected List<?> doList(ApIndent searchForm, int start, int limit, List<Order> orders) throws Exception {
        return service.list(searchForm, start, limit, orders);
    }

    @RequestMapping(value = "apIndentList")
    protected String apartyList(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "apBatchId", required = true) String apBatchId,
            @RequestParam(value = "managerId", required = false) String managerId,
            @RequestParam(value = "workerId", required = false) String workerId) {
        
        model.addAttribute("apBatchId", apBatchId);
        model.addAttribute("managerId", managerId);
        model.addAttribute("workerId", workerId);
        
        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/apIndentList";
    }
}