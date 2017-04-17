package com.company.api.schedule.job;

import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.company.api.dict.service.EnumYesno;
import com.company.api.indent.service.IndentEvaluateService;
import com.company.api.indent.service.IndentService;
import com.company.po.indent.Indent;
import com.company.po.indent.IndentEvaluate;
import com.company.context.SpringContextHolder;
import com.company.util.DateUtil;

/**
 * 订单评价到期  自动处理
 * 
 * @author liurengjie
 *
 */
public class IndentOvertimeEvaluateJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        IndentEvaluateService indentEvaluateService = SpringContextHolder.getBean(IndentEvaluateService.BEAN_NAME);
        IndentService indentService = SpringContextHolder.getBean(IndentService.BEAN_NAME);
        Date today = new Date();
        Date date = DateUtil.addDays(today, -5);
        List<Indent> indentList = indentService.findOverTimeEvaluate(date);
        for (Indent indent : indentList) {
            IndentEvaluate indentEvaluate = new IndentEvaluate();
            indentEvaluate.setIndentId(indent.getId());
            indentEvaluate.setWorkerId(indent.getWorkerId());
            indentEvaluate.setWorkdone(EnumYesno.YES.name());
            //自动评价逻辑
            indentEvaluateService.saveEvalute(indentEvaluate);
        }
    }

}
