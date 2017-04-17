package com.company.api.schedule.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.company.api.mgr.service.ManagerService;
import com.company.api.wk.service.WorkerService;
import com.company.context.SpringContextHolder;

/**
 * 定期更新排名
 * 
 * @author lihome
 *
 */
public class RankJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        
        WorkerService workerService = SpringContextHolder.getBean(WorkerService.BEAN_NAME);
        workerService.reSetRank();
        
        ManagerService managerService = SpringContextHolder.getBean(ManagerService.BEAN_NAME);
        managerService.reSetRank();
    }

}
