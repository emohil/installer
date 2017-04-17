package com.company.api.schedule.job;

import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.company.api.indent.service.IndentService;
import com.company.po.indent.Indent;
import com.company.context.SpringContextHolder;
import com.company.util.DateUtil;
import com.company.util.StringUtil;

/**
 * 订单超时  未抢处理
 * 
 * @author liurengjie
 *
 */
public class ScrambleIndentOvertimeJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        IndentService indentService = SpringContextHolder.getBean(IndentService.BEAN_NAME);
        //过期未抢的上门单
        Date today = new Date();
        Date date = DateUtil.addHours(today, -2);
        List<Indent> over2TimeIndent = indentService.unScrambleOverTimeIndent(date);
        for (Indent indent : over2TimeIndent) {
            if (StringUtil.isNotBlank(indent.getWorkerId())) {
                indentService.clearWorkerIdAndPush(indent);
            }
        }
        Date date2 = DateUtil.addHours(today, -4);
        List<Indent> over4TimeIndent = indentService.unScrambleOverTimeIndent(date2);
        for (Indent indent : over4TimeIndent) {
            if (StringUtil.isNotBlank(indent.getManagerId())) {
                indentService.clearManagerIdAndPush(indent);
            } else {
                indentService.pushToAllWorker(indent);
            }
        }
    }

}
