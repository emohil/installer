package com.company.repo.schedule.impl;

import org.springframework.stereotype.Repository;

import com.company.po.schedule.Schedule;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.schedule.ScheduleDao;

@Repository(ScheduleDao.BEAN_NAME)
public class ScheduleDaoImpl extends StringIdBaseDaoImpl<Schedule> //
        implements ScheduleDao {

}
