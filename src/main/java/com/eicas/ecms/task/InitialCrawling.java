package com.eicas.ecms.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eicas.ecms.entity.CronCrawl;
import com.eicas.ecms.mapper.CronCrawlMapper;
import com.eicas.ecms.mapper.EcmsMapper;
import com.eicas.ecms.service.QuartzService;
import liquibase.pro.packaged.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class InitialCrawling {

    @Autowired
    private CronCrawlMapper cronCrawlMapper;

    @Autowired
    private QuartzService quartzService;

    @PostConstruct
    public void InitialC() {
        List<CronCrawl> cronCrawls = cronCrawlMapper.selectList(new QueryWrapper<CronCrawl>().eq("is_deleted", 0));
        if (cronCrawls == null) {
            System.out.println("暂无任何任务可以初始化采集==============================================");
        } else {
            for (int i = 0; i < cronCrawls.size(); i++) {
                quartzService.addJob(cronCrawls.get(i).getJName(), cronCrawls.get(i).getJGroup(), cronCrawls.get(i).getTName(), cronCrawls.get(i).getTGroup(), cronCrawls.get(i).getCron(),cronCrawls.get(i).getRuleId());
            }
        }

    }
}
