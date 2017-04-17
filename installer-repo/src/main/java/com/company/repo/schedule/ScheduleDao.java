package com.company.repo.schedule;

import com.company.po.schedule.Schedule;
import com.company.repo.fw.dao.StringIdBaseDao;

public interface ScheduleDao extends StringIdBaseDao<Schedule> {

    String BEAN_NAME = "scheduleDao";
}
