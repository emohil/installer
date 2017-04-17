package com.company.web.district.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.dict.service.EnumEnableStatus;
import com.company.api.district.service.ProvService;
import com.company.po.district.Prov;
import com.company.web.BasePagerController;
import com.company.dto.Order;

@Controller("webProvController")
@RequestMapping(value = "/prov")
public class ProvController extends BasePagerController<Prov, Prov> {

    private static final String PATH = "prov";

    @Autowired
    private ProvService service;

    @Autowired
    public void setBaseService(ProvService service) {
        super.setBaseService(service);
        this.service = service;
    }

    @RequestMapping(value = "/provAdd")
    public String provAdd(HttpServletRequest request, ModelMap model) {

        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/provAdd";
    }

    @RequestMapping(value = "/provEdit")
    public String provEdit(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "id") String id) {

        model.put(TAG_ID, id);
        model.put(TAG_PATH, PATH);
        return PATH + "/provEdit";
    }

    @Override
    protected int doCount(Prov sf) throws Exception {
        return service.count(sf);
    }

    @Override
    protected List<?> doList(Prov sf, int start, int limit, List<Order> orders) throws Exception {

        return service.queryProvList();
    }
    
    @RequestMapping(value = "/provList")
    protected String provList() {
        return PATH + "/provList";
    }

    @RequestMapping(value = "/queryProvList")
    @ResponseBody
    public List<Prov> queryProvList() {

        return service.queryProvList();
    }

    @RequestMapping(value = "/openProv")
    public String openProv(@RequestParam(value = "id") String id) {

        Prov prov = service.find(id);
        prov.setStatus(EnumEnableStatus.ENABLED.name());
        service.update(prov);
        return "redirect:/prov/provList.do";

    }

    @RequestMapping(value = "/closeProv")
    public String closeProv(@RequestParam(value = "id") String id) {

        Prov prov = service.find(id);
        prov.setStatus(EnumEnableStatus.DISABLED.name());
        service.update(prov);
        return "redirect:/prov/provList.do";
    }
}