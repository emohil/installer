package com.company.web.indent.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.indent.service.IndentNodeService;
import com.company.dto.indentnode.IndentNodeTreeItem;
import com.company.po.indent.IndentNode;
import com.company.web.BaseEntryController;
import com.company.util.StringUtil;

@Controller("webIndentNodeController")
@RequestMapping(value = "/indentNode")
public class IndentNodeController extends BaseEntryController<IndentNode> {
    
    private static final String PATH = "indent";
    
    private IndentNodeService service;
    
    @Autowired
    public void setBaseService(IndentNodeService service) {
        super.setBaseService(service);
        this.service = service;
    }
    
    /**
     * 跳转到服务节点 Tree 页面
     * 
     * @return
     */
    @RequestMapping(value = "/indentNodeTree")
    public String indentNodeTree(@RequestParam(value = "id", required = false) String id, 
            ModelMap model) {
        if (StringUtil.isNotEmpty(id)) {
            model.put(TAG_ID, id);
        }
        return PATH + "/indentNodeTree";
    }

    /**
     * 加载Tree数据
     * 
     * @return
     */
    @RequestMapping(value = "/loadTreeDatas")
    @ResponseBody
    public List<IndentNodeTreeItem> loadTreeDatas(@RequestParam(value = "id", required = false) String id) {

        if (StringUtil.isEmpty(id)) {
            return null;
        }
        
        return service.treeItems(id);
    }
    
}