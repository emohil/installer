package com.company.web.sctype.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.sctype.service.SctypeService;
import com.company.dto.sctype.SctypeTreeItem;
import com.company.po.sctype.Sctype;
import com.company.web.BaseEntryController;

@Controller("webSctypeController")
@RequestMapping(value = "/sctype")
public class SctypeController extends BaseEntryController<Sctype> {

    private static final String PATH = "sctype/sctype";
    
    private SctypeService service;
    
    @Autowired
    public void setBaseService(SctypeService service) {
        super.setBaseService(service);
        
        this.service = service;
    }

    
    @RequestMapping(value = "/sctypeAdd")
    public String sctypeAdd(HttpServletRequest request, ModelMap model) {
        
        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/sctypeAdd";
    }

    @RequestMapping(value = "/sctypeEdit")
    public String serveTypeEdit(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "id") String id) {

        model.put(TAG_ID, id);
        model.put(TAG_PATH, PATH);
        return PATH + "/sctypeEdit";
    }

    /**
     * 跳转到服务类别 Tree 页面
     * 
     * @return
     */
    @RequestMapping(value = "/sctypeTree")
    public String sctypeTree() {
        return PATH + "/sctypeTree";
    }

    /**
     * 加载Tree数据
     * 
     * @return
     */
    @RequestMapping(value = "/loadTreeDatas")
    @ResponseBody
    public List<SctypeTreeItem> loadTreeDatas() {

        return service.treeItems();
    }
}