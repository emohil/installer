package com.company.web.schedule.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.schedule.ScheduleService;
import com.company.po.schedule.Schedule;
import com.company.web.BasePagerController;
import com.company.util.Dto;

@Controller("webScheduleController")
@RequestMapping(value = "/schedule")
public class ScheduleController extends BasePagerController<Schedule, Schedule> {

    private static final String PATH = "schedule";

    private ScheduleService service;

    @Autowired
    public void setBaseService(ScheduleService service) {
        super.setBaseService(service);
        super.setPagerService(service);
        this.service = service;
    }

    @RequestMapping(value = "scheduleList")
    public String scheduleList(HttpServletRequest request, ModelMap model) {
        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/scheduleList";
    }

    @RequestMapping(value = "doEnable")
    @ResponseBody
    public Dto doEnable(HttpServletRequest request, @RequestParam(value = "id", required = true) String id) {
        return service.enable(id);
    }

    @RequestMapping(value = "doDisable")
    @ResponseBody
    public Dto doDisable(HttpServletRequest request, @RequestParam(value = "id", required = true) String id) {
        return service.disable(id);
    }
    
    @RequestMapping(value = "doTrigger")
    @ResponseBody
    public Dto doTrigger(HttpServletRequest request, @RequestParam(value = "id", required = true) String id) {
        return service.trigger(id);
    }
}
