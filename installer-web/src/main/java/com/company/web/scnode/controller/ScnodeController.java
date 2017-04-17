package com.company.web.scnode.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.scnode.service.ScnodeService;
import com.company.dto.scnode.ScnodeTreeItem;
import com.company.po.scnode.Scnode;
import com.company.web.BaseEntryController;

@Controller("webScnodeController")
@RequestMapping(value = "/scnode")
public class ScnodeController extends BaseEntryController<Scnode> {
    
    private static final String PATH = "scnode/scnode";
    
    private ScnodeService service;
    
    @Autowired
    public void setBaseService(ScnodeService service) {
        super.setBaseService(service);
        this.service = service;
    }
    
    
    @RequestMapping(value = "/scnodeAdd")
    public String scnodeAdd(HttpServletRequest request, ModelMap model) {
        
        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/scnodeAdd";
    }

    @RequestMapping(value = "/scnodeEdit")
    public String scnodeEdit(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "id") String id) {

        model.put(TAG_ID, id);
        model.put(TAG_PATH, PATH);
        return PATH + "/scnodeEdit";
    }

    /**
     * 跳转到服务节点 Tree 页面
     * 
     * @return
     */
    @RequestMapping(value = "/scnodeTree")
    public String sctypeTree() {
        return PATH + "/scnodeTree";
    }

    /**
     * 加载Tree数据
     * 
     * @return
     */
    @RequestMapping(value = "/loadTreeDatas")
    @ResponseBody
    public List<ScnodeTreeItem> loadTreeDatas() {

        return service.treeItems();
    }
}