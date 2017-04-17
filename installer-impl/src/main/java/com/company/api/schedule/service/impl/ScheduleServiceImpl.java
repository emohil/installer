package com.company.api.schedule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.dict.service.EnumEnableStatus;
import com.company.api.fw.service.impl.PagerBaseServiceImpl;
import com.company.api.fw.service.impl.query.CompareOps;
import com.company.api.fw.service.impl.query.QueryInfoBuilder;
import com.company.api.schedule.ScheduleService;
import com.company.api.schedule.SchedulerManager;
import com.company.po.schedule.Schedule;
import com.company.context.SpringContextHolder;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.schedule.ScheduleDao;
import com.company.util.Dto;

@Service(ScheduleService.BEAN_NAME)
public class ScheduleServiceImpl extends PagerBaseServiceImpl<Schedule, Schedule>//
        implements ScheduleService {

    @Autowired
    private SqlDao sqlDao;
    
    @Autowired
    public void setBaseDao(ScheduleDao dao) {
        super.setBaseDao(dao);
    }
    

    @Override
    public QueryInfoBuilder prepareQuery(Schedule sf) {
        if (sf == null) {
            sf = new Schedule();
        }
        StringBuilder sql = new StringBuilder()//
                .append("select * from ZL_SCHEDULE")//
                .append(" WHERE 1=1");

        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql.toString())//
                .and("NAME1", CompareOps.CONTAINS, sf.getName1())//
                .and("QUALIFIED_NAME", CompareOps.CONTAINS, sf.getQualifiedName())//
                ;

        return builder;
    }
    

    @Override
    public List<Schedule> listEnabled() {
        String sql = "select * from ZL_SCHEDULE where STATUS=?";
        return sqlDao.listAll(sql, Schedule.class, new Object[] {EnumEnableStatus.ENABLED.name()});
    }

    @Override
    public Dto enable(String id) {
        Dto ret = new Dto();
        Schedule schedule = find(id);
        schedule.setStatus(EnumEnableStatus.ENABLED.name());

        SchedulerManager schedulerManager = SpringContextHolder.getBean(SchedulerManager.class);

        schedulerManager.scheduleJob(schedule);

        ret.put(TAG_SUCCESS, true);
        return ret;
    }

    @Override
    public Dto disable(String id) {
        Dto ret = new Dto();
        Schedule schedule = find(id);
        schedule.setStatus(EnumEnableStatus.DISABLED.name());

        SchedulerManager schedulerManager = SpringContextHolder.getBean(SchedulerManager.class);

        schedulerManager.deleteJob(schedule);

        ret.put(TAG_SUCCESS, true);
        return ret;
    }
    
    
    @Override
    public Dto trigger(String id) {
        Dto ret = new Dto();
        Schedule schedule = find(id);
        
        SchedulerManager schedulerManager = SpringContextHolder.getBean(SchedulerManager.class);
        
        if (schedulerManager.triggerJob(schedule)) {
            ret.put(TAG_SUCCESS, true);
        } else {
            ret.put(TAG_MSG, "运行失败");
        }
        
        return ret;
    }
    
}