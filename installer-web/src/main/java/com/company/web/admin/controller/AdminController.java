package com.company.web.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.admin.service.AdminService;
import com.company.api.role.service.RoleService;
import com.company.po.admin.Admin;
import com.company.web.BasePagerController;
import com.company.dto.Order;
import com.company.util.Dto;

@Controller("webAdminController")
@RequestMapping(value = "/admin")
public class AdminController extends BasePagerController<Admin, Admin> {

    private static final String PATH = "admin";

    private AdminService service;

    @Autowired
    private RoleService roleService;
    
    
    @Autowired
    public void setBaseService(AdminService service) {
        super.setBaseService(service);
        
        this.service = service;
    }

    @Override
    protected int doCount(Admin sf) throws Exception {
        return service.count(sf);
    }

    @Override
    protected List<?> doList(Admin sf, int start, int limit, List<Order> orders) throws Exception {
        return service.list(sf, start, limit, orders);
    }

    @RequestMapping(value = "adminList")
    protected String adminList(HttpServletRequest request, ModelMap model) {
        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/adminList";
    }

    @RequestMapping(value = "/adminAdd")
    public String adminAdd(HttpServletRequest request, ModelMap model) {

        model.addAttribute("roleList", roleService.findAll());

        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/adminAdd";
    }

    @RequestMapping(value = "/adminEdit")
    public String adminEdit(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "id") String id) {

        model.addAttribute("roleList", roleService.findAll());

        model.put(TAG_ID, id);
        model.put(TAG_PATH, PATH);
        return PATH + "/adminEdit";
    }

    @RequestMapping(value = "disable")
    @ResponseBody
    public Dto disable(@RequestParam(value = "id") String id) {
        Dto dto = new Dto();

        service.disableAdmin(id);

        return dto;
    }

    @RequestMapping(value = "enable")
    @ResponseBody
    public Dto enable(@RequestParam(value = "id") String id) {
        Dto dto = new Dto();

        service.enableAdmin(id);

        return dto;
    }

    @Override
    public Admin load(String id) {
        return service.load(id);
    }
}