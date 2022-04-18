package com.eicas.cms.crawler;

//import com.eicas.cms.component.MyWebsocketServer;

import com.eicas.cms.crawler.driver.WebDriverDownloader;
import com.eicas.cms.pojo.entity.CollectRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import javax.annotation.Resource;

@Slf4j
@Component
public class ArticleSpider {

    @Resource
    private ArticlePageProcessor articlePageProcessor;

    @Resource
    private ArticlePipeline articlePipeline;

    @Value("${filePath.phantomjs}")
    private String phantomJS;

    /**
     * 开启爬虫方法
     *
     * @param collectRule 采集规则
     */
    public void run(CollectRule collectRule) {
        try {
            //设置采集规则
            articlePageProcessor.setCollectRule(collectRule);
            new Spider(articlePageProcessor)
                    .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)))
                    .addUrl(collectRule.getCollectUrl())
                    .setDownloader(new WebDriverDownloader(phantomJS))
                    .addPipeline(articlePipeline)
                    .thread(1)
                    .start();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
