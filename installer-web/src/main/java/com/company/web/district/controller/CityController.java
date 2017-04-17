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
import com.company.api.district.service.CityService;
import com.company.api.district.service.ProvService;
import com.company.dto.city.CityData;
import com.company.po.district.City;
import com.company.po.district.Prov;
import com.company.web.BasePagerController;
import com.company.dto.Order;

@Controller("webCityController")
@RequestMapping(value = "/city")
public class CityController extends BasePagerController<City, City> {

    private static final String PATH = "city";

    @Autowired
    private CityService service;

    @Autowired
    private ProvService provService;

    @Autowired
    public void setBaseService(CityService service) {
        super.setBaseService(service);
        this.service = service;
    }

    @RequestMapping(value = "/cityAdd")
    public String cityAdd(HttpServletRequest request, ModelMap model, @RequestParam(value = "provId") String provId) {

        Prov prov = provService.find(provId);
        model.addAttribute("prov", prov);
        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/cityAdd";
    }

    @RequestMapping(value = "/cityEdit")
    public String cityEdit(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "id") String id) {
        model.put(TAG_ID, id);
        model.put(TAG_PATH, PATH);
        return PATH + "/cityEdit";
    }


    @Override
    protected int doCount(City sf) throws Exception {
        return service.count(sf);
    }

    @Override
    protected List<?> doList(City sf, int start, int limit, List<Order> orders) throws Exception {
        return service.queryByProvId(sf);
    }
    
    @RequestMapping(value = "/cityList")
    protected String cityList(@RequestParam(value = "sf.provId") String provId,
            ModelMap model) {
        model.put("provId", provId);
        
        return PATH + "/cityList";
    }

    @RequestMapping(value = "closeCity")
    public String closeCity(@RequestParam(value = "id") String id, //
            @RequestParam(value = "provId") String provId) {

        City city = service.find(id);
        city.setStatus(EnumEnableStatus.DISABLED.name());
        service.closeCity(city);
        return "redirect:/city/cityList.do?sf.provId=" + provId;
    }

    @RequestMapping(value = "cityData")
    @ResponseBody
    public List<CityData> cityData() {

        return service.cityData();
    }
}