package com.company.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.web.BaseController;
import com.company.web.fw.AdminAccountHelper;

@Controller("logoutController")
@RequestMapping
public class LogoutController extends BaseController {

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        
        AdminAccountHelper.removeAdmin4Session(request);
        
        return "redirect:index.html";
    }
}
