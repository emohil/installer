package com.company.web.sctype.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.scnode.service.ScnodeService;
import com.company.api.sctype.service.SctypeNodeService;
import com.company.po.sctype.Sctype;
import com.company.web.BaseController;
import com.company.util.Dto;

@Controller("webSctypeNodeController")
@RequestMapping(value = "/sctypeNode")
public class SctypeNodeController extends BaseController {

    private static final String PATH = "sctype/node";

    @Autowired
    private SctypeNodeService service;

    @Autowired 
    private ScnodeService scnodeService;

    @RequestMapping(value = "/sctypeNodeEdit")
    public String sctypeNodeEdit(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "sctypeId") String sctypeId) {
        
        model.put("scnodeList", scnodeService.findAll());

        model.put("sctypeId", sctypeId);
        model.put(TAG_PATH, PATH);

        return PATH + "/sctypeNodeEdit";
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Dto update(HttpServletRequest request, @RequestBody Sctype data) {

        Dto ret = new Dto();
        
        service.saveNodes(data);

        ret.put(TAG_SUCCESS, true);

        return ret;
    }

    @RequestMapping("/load")
    @ResponseBody
    public Sctype load(@RequestParam("sctypeId") String sctypeId) {
        
        return service.loadData(sctypeId);
    }

}
