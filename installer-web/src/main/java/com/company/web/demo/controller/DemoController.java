package com.company.web.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.web.BaseController;

@Controller("DemoController")
@RequestMapping(value = "/demo")
public class DemoController extends BaseController {

    private static final String PATH = "demo";

    @RequestMapping(value = "/typehead")
    public String typehead(ModelMap model) {
        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/typehead";
    }

    @RequestMapping(value = "/form")
    public String form() {
        return PATH + "/form";
    }

    @RequestMapping(value = "formSave")
    @ResponseBody
    public void fromSave(HttpServletRequest request) {

        System.out.println(request);
    }
}
