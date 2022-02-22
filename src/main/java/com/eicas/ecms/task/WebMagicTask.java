package com.eicas.ecms.task;

import com.eicas.ecms.mapper.CollectionRuleMapper;

import com.eicas.ecms.service.IEcmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class WebMagicTask {

    final private CollectionRuleMapper collectionRuleMapper;

    final private IEcmsService ecmsService;



    @Autowired
    public WebMagicTask(CollectionRuleMapper collectionRuleMapper, IEcmsService ecmsService) {
        this.collectionRuleMapper = collectionRuleMapper;
        this.ecmsService = ecmsService;
    }

//    private ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();

    @Scheduled(cron = "0 0 11 * * *")
    public void BuDuiPush() {
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String format1 = format.format(date);
        for (String s : collectionRuleMapper.selectColumnId(format1)) {
            ecmsService.getQ(s);
        }

    }

}
