package com.company.web.district.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.district.service.AreaService;
import com.company.api.district.service.CityService;
import com.company.po.district.Area;
import com.company.po.district.City;
import com.company.web.BaseEntryController;

@Controller("webAreaController")
@RequestMapping(value = "/area")
public class AreaController extends BaseEntryController<Area> {

    private static final String PATH = "area";

    @Autowired
    private AreaService service;
    
    @Autowired
    private CityService cityService;
    
    @Autowired
    public void setBaseService(AreaService service) {
        super.setBaseService(service);
        this.service = service;
    }
    
    @RequestMapping(value = "/areaAdd")
    public String areaAdd(HttpServletRequest request, ModelMap model, 
            @RequestParam(value = "cityId") String cityId, 
            @RequestParam(value = "provId") String provId) {
        
        City city = cityService.find(cityId);
        model.put("provId", provId);
        model.put("data", city);
        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/areaAdd";
    }

    
    @RequestMapping(value = "/queryAreaList")
    @ResponseBody
    public List<Area> queryAreaList(//
            @RequestParam(value = "cityId") String apartyId) {
        
        return service.queryByCityId(apartyId);
    }
}
