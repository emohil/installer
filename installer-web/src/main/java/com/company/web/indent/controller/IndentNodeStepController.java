package com.company.web.indent.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.api.fs.service.FileManagerService;
import com.company.api.indent.service.IndentNodeService;
import com.company.api.indent.service.IndentNodeStepItemService;
import com.company.api.indent.service.IndentNodeStepService;
import com.company.po.fs.FileIndex;
import com.company.po.indent.IndentNode;
import com.company.po.indent.IndentNodeStep;
import com.company.po.indent.IndentNodeStepItem;
import com.company.web.BasePagerController;
import com.company.dto.Filter;
import com.company.dto.Order;

@Controller("webIndentNodeStepController")
@RequestMapping(value = "/indentNodeStep")
public class IndentNodeStepController extends BasePagerController<IndentNodeStep, IndentNodeStep> {

    private static final String PATH = "indent";
    
    @Autowired
    private IndentNodeStepService service;
    
    @Autowired
    private IndentNodeService indentNodeservice;
    
    @Autowired
    private IndentNodeStepItemService iNSIService;
    
    @Autowired
    private FileManagerService fileManagerService;

    @Autowired
    public void setBaseService(IndentNodeStepService service) {
        this.service = service;
    }
  
    @Override
    protected int doCount(IndentNodeStep searchForm) throws Exception {
        return 0;
    }

    @Override
    protected List<?> doList(IndentNodeStep searchForm, int start, int limit,
            List<Order> orders) throws Exception {
        return null;
    }
    
    @RequestMapping(value = "/findImg")
    public String findImg(@RequestParam String id, 
            ModelMap model) {
        IndentNodeStep indentNodeStep = service.find(id);
        IndentNode indentNode = indentNodeservice.find(indentNodeStep.getIndentNodeId());
        List<IndentNodeStepItem> indentNodeStepItemList = iNSIService.findList(Filter.eq("indentNodeStepId", id));
        for (IndentNodeStepItem indentNodeStepItem : indentNodeStepItemList) {
            List<FileIndex> fileIndexList = fileManagerService.findByBelongToAndExts(indentNodeStepItem.getId(), "INDENT", "ITEM");
            if (fileIndexList != null && fileIndexList.size() > 0) {
                for (FileIndex fileIndex : fileIndexList) {
                    fileIndex.setFileUrl(fileManagerService.getFileUrlById(fileIndex.getId()));
                    fileIndex.setThumbUrl(fileManagerService.getThumbUrlById(fileIndex.getId()));
                }
            }
            indentNodeStepItem.setImgUrlList(fileIndexList);
            
        }
        model.put("indentNodeStepItems", indentNodeStepItemList);
        model.put(TAG_ID, indentNode.getIndentId());
        return PATH + "/indentNodeImg";
    }
    
}