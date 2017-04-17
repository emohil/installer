package com.company.web.sctype.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.fw.DictCodes;
import com.company.api.sctype.service.SctypeContentService;
import com.company.api.sctype.service.SctypeSortService;
import com.company.po.sctype.SctypeContent;
import com.company.po.sctype.SctypeSort;
import com.company.web.BaseEntryController;
import com.company.api.fw.service.SysDictService;
import com.company.dto.SysDict;

@Controller("webSctypeContentController")
@RequestMapping(value = "/sctypeContent")
public class SctypeContentController extends BaseEntryController<SctypeContent> {

    private static final String PATH = "sctype/content";

    private SctypeContentService service;

    @Autowired
    private SctypeSortService sctypeSortService;

    @Autowired
    private SysDictService sysDictService;
    
    @Autowired
    public void setBaseService(SctypeContentService service) {
        super.setBaseService(service);
        
        this.service = service;
    }

    @RequestMapping(value = "/sctypeContentAdd")
    public String sctypeContentAdd(HttpServletRequest request, ModelMap model,
            @RequestParam(value = "sctypeSortId") String sctypeSortId) {

        model.addAttribute("uomList", sysDictService.listGroupCopy(DictCodes.UOM));

        SctypeSort sctypeSort = sctypeSortService.find(sctypeSortId);

        model.addAttribute("sctypeSort", sctypeSort);

        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/sctypeContentAdd";
    }

    @RequestMapping(value = "/sctypeContentEdit")
    public String sctypeContentEdit(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "id") String id) {

        model.addAttribute("uomList", sysDictService.listGroupCopy(DictCodes.UOM, SysDict.EMPTY));

        model.put(TAG_ID, id);
        model.put(TAG_PATH, PATH);
        return PATH + "/sctypeContentEdit";
    }

    @Override
    @RequestMapping("/load")
    @ResponseBody
    public SctypeContent load(String id) {
        return service.load(id);
    }
}