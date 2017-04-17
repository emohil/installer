package com.company.api.schedule;

import java.util.List;

import com.company.po.schedule.Schedule;
import com.company.api.fw.service.PagerService;
import com.company.api.fw.service.StringIdBaseService;
import com.company.util.Dto;

public interface ScheduleService extends StringIdBaseService<Schedule>, //
        PagerService<Schedule, Schedule> {

    String BEAN_NAME = "scheduleService";

    List<Schedule> listEnabled();

    /**
     * 启用一个计划
     * 
     * @param id
     * @return
     */
    Dto enable(String id);

    /**
     * 停用一个计划
     * 
     * @param id
     * @return
     */
    Dto disable(String id);

    /**
     * 触发(执行)一个计划
     * 
     * @param id
     * @return
     */
    Dto trigger(String id);
}
