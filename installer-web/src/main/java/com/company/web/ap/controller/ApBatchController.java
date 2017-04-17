package com.company.web.ap.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.ap.service.ApBatchService;
import com.company.po.ap.ApBatch;
import com.company.sf.ap.ApBatchSf;
import com.company.web.BasePagerController;
import com.company.dto.Order;
import com.company.util.Dto;

@Controller("webApBatchController")
@RequestMapping(value = "/apBatch")
public class ApBatchController extends BasePagerController<ApBatchSf, ApBatch> {

    private static final String PATH = "ap/apBatch";

    private ApBatchService service;

    @Autowired
    public void setBaseService(ApBatchService service) {
        super.setBaseService(service);
        this.service = service;
    }

    @Override
    protected int doCount(ApBatchSf searchForm) throws Exception {
        return service.count(searchForm);
    }

    @Override
    protected List<?> doList(ApBatchSf searchForm, int start, int limit, List<Order> orders) throws Exception {
        return service.list(searchForm, start, limit, orders);
    }

    @RequestMapping(value = "apBatchList")
    protected String apartyList(HttpServletRequest request, ModelMap model) {
        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/apBatchList";
    }

    @RequestMapping(value = "/apBatchAdd")
    public String apartyAdd(HttpServletRequest request, ModelMap model) {
        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/apBatchAdd";
    }

    @RequestMapping(value = "/apBatchDisp")
    public String apBatchDisp(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = TAG_ID) String id) {
        model.put(TAG_ID, id);
        model.put(TAG_PATH, PATH);
        return PATH + "/apBatchDisp";
    }

    @RequestMapping(value = "/doPreview")
    @ResponseBody
    public Dto doPreview(HttpServletRequest request, //
            @RequestBody ApBatch data) {

        return service.doPreview(data);
    }

    @RequestMapping(value = "/doSave")
    @ResponseBody
    public Dto doSave(HttpServletRequest request, //
            @RequestBody ApBatch data) {

        data.setCreateBy(getCurrentUser(request));
        data.setModifyBy(getCurrentUser(request));
        return service.doSave(data);
    }

    @RequestMapping(value = "/doDelete")
    @ResponseBody
    public Dto doSave(HttpServletRequest request, //
            @RequestParam(value = "id") String id) {

        return service.doDelete(id);
    }

    @RequestMapping(value = "/doPost")
    @ResponseBody
    public Dto doPost(HttpServletRequest request, //
            @RequestParam(value = "id") String id) {
        
        return service.doPost(id);
    }
}
