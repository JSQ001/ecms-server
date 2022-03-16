package com.eicas.cms.crawling;

import com.eicas.cms.component.MyWebsocketServer;
import com.eicas.cms.pojo.entity.CollectRule;
import com.eicas.cms.pojo.vo.WebSocketResponseToClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import javax.annotation.Resource;

@Slf4j
@Component
public class Craw {

    @Resource
    private XPathPageProcessor pageProcessor;

    @Resource
    private StorePipeline storePipeline;

    //phantomjs在系统存放的路径
    @Value("${filePath.phantomjs}")
    private String phantomjsPath;


    /**
     * 开启爬虫方法
     * @params CollectRule 采集规则
     */
    public void run(CollectRule collectRule,String sessionId){
        try {
            //设置采集规则
            pageProcessor.setCollectRule(collectRule);

            if(StringUtils.hasText(sessionId)){
                storePipeline.setSessionId(sessionId);
                pageProcessor.setSessionId(sessionId);
            }

            //Spider.create(myPageProcessor)
            new Spider(pageProcessor)
                   .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)))
                    .addUrl(collectRule.getCollectUrl())
                    .setDownloader(new WebDriverDownloader(phantomjsPath))
                    .addPipeline(storePipeline)
                    //开启5个线程抓取
                    .thread(5)
                    //启动爬虫
                    .start();
            MyWebsocketServer.pageQueue.put(sessionId,0);
        }catch (Exception e){
            log.error(e.getMessage());
            if(StringUtils.hasText(sessionId)){
                MyWebsocketServer.sendMessage(sessionId, new WebSocketResponseToClient(202, "爬取失败"));
            }
        }
    }

}
