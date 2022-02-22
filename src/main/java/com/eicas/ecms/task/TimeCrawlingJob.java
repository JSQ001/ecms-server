package com.eicas.ecms.task;

import com.eicas.ecms.service.IEcmsService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.ejb.ConcurrencyManagement;

@Slf4j
@DisallowConcurrentExecution
public class TimeCrawlingJob extends QuartzJobBean {
    @Autowired
    private IEcmsService iEcmsService;
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String id = context.getJobDetail().getJobDataMap().getString("ruleId");
        iEcmsService.getQ(id);
        log.info("开始采集序号为:"+id+"的文章");
    }
}
