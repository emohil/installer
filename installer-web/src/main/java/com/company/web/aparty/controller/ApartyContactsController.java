package com.company.web.aparty.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.aparty.service.ApartyContactsService;
import com.company.api.dict.service.EnumPartyStamp;
import com.company.po.aparty.ApartyContacts;
import com.company.web.BaseController;

@Controller("webApartyContactsController")
@RequestMapping(value = "/apartyContacts")
public class ApartyContactsController extends BaseController {
    
    @Autowired
    private ApartyContactsService service;

    @RequestMapping(value = "/queryContactsList")
    @ResponseBody
    public List<ApartyContacts> queryContactsList(//
            @RequestParam(value = "apartyId") String apartyId, //
            @RequestParam(value = "stamp") EnumPartyStamp stamp) {
        
        return service.queryByApartyAndType(stamp, apartyId);
    }

}
