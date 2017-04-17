package com.company.api.schedule.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.company.api.account.service.AccountService;
import com.company.api.account.service.EnumAccountType;
import com.company.api.fw.util.MessagePush;
import com.company.api.indent.service.EnumJpushType;
import com.company.api.wk.service.WkMgrSlaveService;
import com.company.api.wk.service.WorkerService;
import com.company.po.account.Account;
import com.company.po.wk.WkMgrSlave;
import com.company.po.wk.Worker;
import com.company.context.SpringContextHolder;
import com.company.util.DateUtil;
import com.company.util.StringUtil;

/**
 * 工匠申请加入经理人 超过24小时 自动忽略
 * 
 * @author liurengjie
 *
 */
public class WorkerApplyOverTimeJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        WorkerService workerService = SpringContextHolder.getBean(WorkerService.BEAN_NAME);
        AccountService accountService = SpringContextHolder.getBean(AccountService.BEAN_NAME);
        WkMgrSlaveService wkMgrSlaveService = SpringContextHolder.getBean(WkMgrSlaveService.BEAN_NAME);
        Date today = new Date();
        Date date = DateUtil.addHours(today, -24);
        List<WkMgrSlave> findOverTimeApplys = wkMgrSlaveService.findOverTimeApply(date);
        for (WkMgrSlave wkMgrSlave : findOverTimeApplys) {
            Worker worker = workerService.dealOverTimeApply(wkMgrSlave.getId(), wkMgrSlave.getWorkerId());
            Account managerAccount = accountService.findByManagerId(wkMgrSlave.getManagerId());
            {//推送给工匠忽略请求
                Map<String, String> extras = new HashMap<String, String>();
                extras.put("id", "");
                extras.put("accountType", EnumAccountType.WORKER.name());
                extras.put("pushType", EnumJpushType.MANAGER_CHOOSE.name());
                List<String> workerRegistrationId = new ArrayList<String>();
                if (StringUtil.isNotBlank(worker.getAccount().getRegistrationId())) {
                    workerRegistrationId.add(worker.getAccount().getRegistrationId());
                }
                if (workerRegistrationId.size() > 0) {
                    MessagePush push = new MessagePush("经理人"+ managerAccount.getName1() +"还未处理您的申请，点击详情重新选择经理人吧~", "众联工匠", extras);
                    push.pushMsgToRegistrationIds(workerRegistrationId);
                }
            }
        }
    }
}
