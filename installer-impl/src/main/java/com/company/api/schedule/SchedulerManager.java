package com.company.api.schedule;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.util.ClassUtils;

import com.company.api.schedule.ScheduleService;
import com.company.po.schedule.Schedule;
import com.company.util.StringUtil;

public class SchedulerManager implements ApplicationContextAware {

    private final static Logger log = LoggerFactory.getLogger(SchedulerManager.class);

    private final static String SCHEDULER_GROUP = Scheduler.DEFAULT_GROUP;

    private Scheduler scheduler;

    private void init(ApplicationContext applicationContext) {

        log.info("=======SchedulerManager start");

        SchedulerFactoryBean schedulerFactoryBean = applicationContext.getBean(SchedulerFactoryBean.class);

        this.scheduler = schedulerFactoryBean.getScheduler();
        
        try {
            if (this.scheduler.isInStandbyMode()) {
                this.scheduler.start();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        ScheduleService scheduleService = applicationContext.getBean(ScheduleService.BEAN_NAME, ScheduleService.class);

        List<Schedule> list = scheduleService.listEnabled();
        for (Schedule schedule : list) {
            this.scheduleJob(schedule);
        }

        log.info("=======SchedulerManager end");
    }

    private Trigger createTrigger(Schedule schedule) throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(schedule.getId(), SCHEDULER_GROUP);

        Trigger trigger = this.scheduler.getTrigger(triggerKey);
        if (trigger != null) {
            return trigger;
        }

        String cronExpression = schedule.getCronExpression();
        if (StringUtil.isEmpty(cronExpression)) {
            return null;
        }
        ScheduleBuilder<CronTrigger> scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        trigger = TriggerBuilder.newTrigger()//
                .withSchedule(scheduleBuilder)//
                .withIdentity(triggerKey).build();

        return trigger;
    }

    private JobDetail createJobDetail(Schedule schedule) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(schedule.getId(), SCHEDULER_GROUP);

        JobDetail jobDetail = this.scheduler.getJobDetail(jobKey);
        if (jobDetail != null) {
            return jobDetail;
        }

        Class<?> jobClass = null;
        try {
            jobClass = ClassUtils.forName(schedule.getQualifiedName(), ClassUtils.getDefaultClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        @SuppressWarnings("unchecked")
        JobDetail jobDetail0 = JobBuilder.newJob((Class<? extends Job>) jobClass)//
                .withIdentity(jobKey)//
                .build();

        return jobDetail0;
    }

    public boolean scheduleJob(Schedule schedule) {
        try {
            JobDetail jobDetail = createJobDetail(schedule);
            Trigger trigger = createTrigger(schedule);
            if (jobDetail == null || trigger == null) {
                log.error("Can't startup schedule :{}", schedule);
                return false;
            }
            this.scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean deleteJob(Schedule schedule) {
        JobKey jobKey = JobKey.jobKey(schedule.getId(), SCHEDULER_GROUP);
        try {
            this.scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean triggerJob(Schedule schedule) {
        JobKey jobKey = JobKey.jobKey(schedule.getId(), SCHEDULER_GROUP);

        try {
            this.scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.init(applicationContext);
    }
}