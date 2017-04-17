package com.company.web.item.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.dict.service.EnumPartyStamp;
import com.company.api.item.service.ItemContactsService;
import com.company.po.item.ItemContacts;
import com.company.web.BaseController;

@Controller("webItemContactsController")
@RequestMapping(value = "/itemContacts")
public class ItemContactsController extends BaseController {
    
    @Autowired
    private ItemContactsService service;

    @RequestMapping(value = "/queryContactsList")
    @ResponseBody
    public List<ItemContacts> queryContactsList(//
            @RequestParam(value = "itemId") String itemId, //
            @RequestParam(value = "stamp") EnumPartyStamp stamp) {
        
        return service.queryByItemAndType(stamp, itemId);
    }

}
