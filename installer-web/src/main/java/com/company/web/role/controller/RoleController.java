package com.company.web.role.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.auth.service.AuthGroupService;
import com.company.api.role.service.RoleService;
import com.company.po.role.Role;
import com.company.web.BasePagerController;
import com.company.dto.Order;
import com.company.util.Dto;

@Controller("webRoleController")
@RequestMapping(value = "/role")
public class RoleController extends BasePagerController<Role, Role> {

    private static final String PATH = "role";

    private RoleService service;
    
    @Autowired
    private AuthGroupService authGroupService;
    
    @Autowired
    public void setBaseService(RoleService service) {
        super.setBaseService(service);
        this.service = service;
    }

    @Override
    protected int doCount(Role searchForm) throws Exception {
        return service.count(searchForm);
    }

    @Override
    protected List<?> doList(Role searchForm, int start, int limit, List<Order> orders) throws Exception {
        return service.list(searchForm, start, limit, orders);
    }

    @RequestMapping(value = "roleList")
    protected String roleList(HttpServletRequest request, ModelMap model) {
        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/roleList";
    }

    @RequestMapping(value = "/roleAdd")
    public String roleAdd(HttpServletRequest request, ModelMap model) {

        model.addAttribute("authGroupList", authGroupService.groupListWithAuths());
        
        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/roleAdd";
    }

    @RequestMapping(value = "/roleEdit")
    public String roleEdit(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "id") String id) {

        model.addAttribute("authGroupList", authGroupService.groupListWithAuths());
        
        model.put(TAG_ID, id);
        model.put(TAG_PATH, PATH);
        return PATH + "/roleEdit";
    }

    
    @RequestMapping(value = "/doDelete")
    @ResponseBody
    public Dto delete(@RequestParam(value = "id") String id) {
        
        Dto dto = new Dto();
        
        //TODO check usage status
        service.delete(id);

        return dto;
    }

    @Override
    public Role load(String id) {
        return service.load(id);
    }
}
