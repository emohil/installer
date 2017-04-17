package com.company.web.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.admin.service.EnumAuth;
import com.company.po.admin.Admin;
import com.company.web.BaseController;
import com.company.web.fw.AdminAccountHelper;
import com.company.util.Dto;

@Controller("webAdminAuthController")
@RequestMapping(value = "/adminAuth")
public class AdminAuthController extends BaseController {

    @RequestMapping(value = "/hasIndentListAuth")
    @ResponseBody
    public Dto hasIndentListAuth(HttpServletRequest request) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, true);
        
        Admin admin = AdminAccountHelper.getAdmin4Session(request);
        if (admin == null) {
            return ret;
        }
        
        ret.put(TAG_DATA, admin.getAuthIdList().contains(EnumAuth.indentList.name()));
        
        return ret;
    }
}
