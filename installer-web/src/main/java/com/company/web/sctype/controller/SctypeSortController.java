package com.company.web.sctype.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.fw.DictCodes;
import com.company.api.sctype.service.SctypeService;
import com.company.api.sctype.service.SctypeSortService;
import com.company.po.item.Item;
import com.company.po.sctype.Sctype;
import com.company.po.sctype.SctypeSort;
import com.company.web.BaseEntryController;
import com.company.api.fw.service.SysDictService;
import com.company.util.json.JacksonHelper;

@Controller("webSctypeSortController")
@RequestMapping(value = "/sctypeSort")
public class SctypeSortController extends BaseEntryController<SctypeSort> {

    private static final String PATH = "sctype/sort";

    private SctypeSortService service;
    
    @Autowired
    private SysDictService sysDictService;

    @Autowired
    public void setBaseService(SctypeSortService service) {
        super.setBaseService(service);

        this.service = service;
    }

    @Autowired
    private SctypeService sctypeService;

    @RequestMapping(value = "/sctypeSortAdd")
    public String serveSortAdd(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "sctypeId") String sctypeId) {

        Sctype sctype = sctypeService.find(sctypeId);
        
        model.addAttribute("serviceSortList", sysDictService.listGroupCopy(DictCodes.SERVICE_SORT));
        model.addAttribute("sctype", sctype);
        model.addAttribute(TAG_PATH, PATH);
        
        return PATH + "/sctypeSortAdd";
    }

    @RequestMapping(value = "/sctypeSortEdit")
    public String serveSortEdit(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "id") String id) {

        model.put(TAG_ID, id);
        model.put(TAG_PATH, PATH);
        return PATH + "/sctypeSortEdit";
    }
    
    
    @Override
    @RequestMapping("/load")
    @ResponseBody
    public SctypeSort load(String id) {
        return service.load(id);
    }
    

    @RequestMapping(value = "/typeList")
    @ResponseBody
    public List<Sctype> typeList(ModelMap model, @RequestParam(value = "sortIds") List<String> sortIds,
            @RequestParam(value = "data") String data) {
        Item item = JacksonHelper.toObject(data, Item.class);
        List<Sctype> typeList = service.typeList(sortIds);
        return service.calculatePrice(typeList, item);
    }
}