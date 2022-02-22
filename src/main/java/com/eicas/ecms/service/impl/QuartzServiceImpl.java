package com.eicas.ecms.service.impl;

import com.eicas.ecms.service.QuartzService;
import com.eicas.ecms.task.TimeCrawlingJob;
import liquibase.pro.packaged.S;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuartzServiceImpl implements QuartzService {
//    private static String jName ="CrawlingJob";
//    private static String jGroup="CrawlingGroup";
//    private static String tName="CrawlingTigger";
//    private static String tGroup="TiggerJob";


    @Autowired
    private Scheduler scheduler;

    /**
     * 新增一个定时任务
     * @param jName 任务名称
     * @param jGroup 任务组
     * @param tName 触发器名称
     * @param tGroup 触发器组
     * @param cron cron表达式
     */
    @Override
    public void addJob(String jName, String jGroup, String tName, String tGroup, String cron,String ruleId) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(TimeCrawlingJob.class)
                    .withIdentity(jName, jGroup)
                    .usingJobData("ruleId", ruleId)
                    .build();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(tName, tGroup)
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .build();
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
            System.out.println("添加了任务"+ruleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停定时任务
     * @param jName 任务名
     * @param jGroup 任务组
     */
    @Override
    public void pauseJob(String jName, String jGroup) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jName, jGroup));
            System.out.println("真的暂停了了"+jName+"的任务哦");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 继续定时任务
     * @param jName 任务名
     * @param jGroup 任务组
     */
    @Override
    public void resumeJob(String jName, String jGroup) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jName, jGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除定时任务
     * @param jName 任务名
     * @param jGroup 任务组
     */
    @Override
    public void deleteJob(String jName, String jGroup) {
        try {
            scheduler.deleteJob(JobKey.jobKey(jName, jGroup));
            System.out.println("真的删除了"+jName+"的任务哦");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复所有任务（暂定）
     */
    public void resumall() {
        try {
            scheduler.resumeAll();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停所有任务
     */
    public void test() {
        try {
            scheduler.pauseAll();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新任务
     * @param triggerName
     * @param triggerGroupName
     * @param cron
     */
    public void refreshTask(String triggerName, String triggerGroupName, String cron) {
        //是通过tName和tGroup修改参数的 tName唯一
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                /** 方式一 ：调用 rescheduleJob 开始 */
                // 触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                System.out.println("真的修改了"+triggerName+"的任务哦");
                scheduler.rescheduleJob(triggerKey, trigger);
                /** 方式一 ：调用 rescheduleJob 结束 */

                /** 方式二：先删除，然后在创建一个新的Job  */
                //JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
                //Class<? extends Job> jobClass = jobDetail.getJobClass();
                //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
                //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
                /** 方式二 ：先删除，然后在创建一个新的Job */
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
