package com.company.web.aparty.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.aparty.service.ApartyService;
import com.company.api.dict.service.EnumEnableStatus;
import com.company.api.fw.DictCodes;
import com.company.api.fw.EnumCodes;
import com.company.po.aparty.Aparty;
import com.company.sf.aparty.ApartySf;
import com.company.web.BasePagerController;
import com.company.api.fw.service.SysDictService;
import com.company.dto.Order;
import com.company.dto.SysDict;
import com.company.dto.ValueTextPair;
import com.company.util.Dto;

@Controller("webApartyController")
@RequestMapping(value = "/aparty")
public class ApartyController extends BasePagerController<ApartySf, Aparty> {

    private static final String PATH = "aparty";

    @Autowired
    private ApartyService service;
    
    @Autowired
    private SysDictService sysDictService;

    @Autowired
    public void setBaseService(ApartyService service) {
        super.setBaseService(service);
        this.service = service;
    }

    @Override
    protected int doCount(ApartySf sf) throws Exception {
        return service.count(sf);
    }

    @Override
    protected List<?> doList(ApartySf sf, int start, int limit, List<Order> orders) throws Exception {
        return service.list(sf, start, limit, orders);
    }
    
    @RequestMapping(value = "apartyList")
    protected String apartyList(HttpServletRequest request, ModelMap model) {
        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/apartyList";
    }

    
    @RequestMapping(value = "/apartyAdd")
    public String apartyAdd(HttpServletRequest request, ModelMap model) {
        model.put("revolveTimeList",  sysDictService.listGroupCopy(EnumCodes.REVOLVE_TIME));
        model.addAttribute("industryList", sysDictService.listGroupCopy(DictCodes.INDUSTRY, SysDict.CHECK));
        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/apartyAdd";
    }

    @RequestMapping(value = "/apartyEdit")
    public String apartyEdit(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = TAG_ID) String id) {
        model.put("revolveTimeList",  sysDictService.listGroupCopy(EnumCodes.REVOLVE_TIME));
        model.addAttribute("industryList", sysDictService.listGroupCopy(DictCodes.INDUSTRY, SysDict.CHECK));
        model.put(TAG_ID, id);
        model.put(TAG_PATH, PATH);
        return PATH + "/apartyEdit";
    }

    
    @RequestMapping(value = "/doTypeahead")
    @ResponseBody
    public List<ValueTextPair> doTypeahead(@RequestBody Dto params) {
        return service.doTypeahead(params);
    }
    
    @RequestMapping(value = "/deleteAparty")
    @ResponseBody
    public Dto deleteAparty(@RequestParam(value = "id") String id) {
        
        Dto rt = service.deleteAparty(id);
        
        return rt;
    }
    
    @RequestMapping(value = "/doSave")
    @ResponseBody
    public Dto doSave(@RequestBody Aparty data, ModelMap model) {
        Dto rt = new Dto();
        Aparty aparty = service.findApartyByCode1(data.getCode1());
        if (aparty != null) {
            rt.put(TAG_SUCCESS, false);
            rt.put(TAG_MSG, "甲方编号重复，请输入其他编号");
            return rt;
        }
        
        service.save(data);
        rt.put(TAG_SUCCESS, true);
        rt.put(TAG_MSG, "成功录入该甲方信息");
        return rt;
    }
    
    @RequestMapping(value = "/controlAparty")
    @ResponseBody
    public Dto controlAparty(@RequestParam(value = "id") String id,
            @RequestParam(value = "mark") String mark) {
        Dto rt = new Dto();
        Aparty aparty = service.find(id);
        if (mark.equals("1")) {
            aparty.setApartyStatus(EnumEnableStatus.DISABLED.name());
        } else {
            aparty.setApartyStatus(EnumEnableStatus.ENABLED.name());
        }
        service.update(aparty);
        rt.put(TAG_SUCCESS, true);
        return rt;
    }
    
    
}