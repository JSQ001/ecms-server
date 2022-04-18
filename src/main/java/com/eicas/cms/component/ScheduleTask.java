package com.eicas.cms.component;

import com.eicas.cms.crawler.ArticleSpider;
import com.eicas.cms.pojo.entity.CollectRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;


@Component
@Slf4j
public class ScheduleTask {

    private Map<Long,ScheduledFuture> scheduleMap = new HashMap<>();

    @Resource
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Resource
    private ArticleSpider articleSpider;

    /**
    *  更新定时任务队列
    * */
    public void update(CollectRule collectRule){
        try {
            if(scheduleMap.get(collectRule.getId()) != null){
                remove(collectRule);
            }
            if(collectRule.getAutoCollectTime() != null) {
                add(collectRule);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    /**
     *  添加定时任务
     * */
    private void add(CollectRule collectRule){
        int hour =  collectRule.getAutoCollectTime().getHour();
        int m =  collectRule.getAutoCollectTime().getMinute();
        System.out.println(hour);
        System.out.println(m);
        String trigger = "0 " + collectRule.getAutoCollectTime().getMinute() + " " +
                collectRule.getAutoCollectTime().getHour() + " * * ?";

        ScheduledFuture future = threadPoolTaskScheduler.schedule(()->{
            log.info("开始爬取---" + collectRule.getId() + "---" + collectRule.getName());
            articleSpider.run(collectRule);
            log.info("爬取结束---" + collectRule.getId() + "---" + collectRule.getName());
        }, new CronTrigger(trigger));
        log.info("已为" + collectRule.getId() + "" + collectRule.getName() +" 添加了定时采集任务,定时时间为：" +
                        collectRule.getAutoCollectTime().getHour() + ":" +collectRule.getAutoCollectTime().getMinute()
                );
        scheduleMap.put(collectRule.getId(),future);
    }

    /**
     *  删除定时任务
     * */
    private void remove(CollectRule collectRule) {
        ScheduledFuture scheduledFuture = scheduleMap.get(collectRule.getId());
        scheduledFuture.cancel(true);
        scheduleMap.remove(scheduledFuture);
    }
}
