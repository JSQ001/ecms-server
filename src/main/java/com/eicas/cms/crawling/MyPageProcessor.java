package com.eicas.cms.crawling;

import com.eicas.cms.pojo.entity.CollectRule;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.Closeable;
import java.io.IOException;

/**
* PageProcessor的定制分为三个部分，分别是爬虫的配置、页面元素的抽取和链接的发现。
* */
@Component
@Getter
@Setter
@Slf4j
public class MyPageProcessor implements PageProcessor, Closeable {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(5).setSleepTime(5000);

    //采集规则
    private CollectRule collectRule;

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        try{
            // 部分二：定义如何抽取页面信息，并保存下来
            page.putField("source", page.getUrl().toString());
            page.putField("title", page.getHtml().xpath(collectRule.getTitleRule()).toString());
            page.putField("author", page.getHtml().xpath(collectRule.getAuthorRule()).toString());
            page.putField("content", page.getHtml().xpath(collectRule.getContentRule()).toString());
            page.putField("columnId", collectRule.getColumnId());
            if(collectRule.getSubTitleRule() != null){
                page.putField("subTitle", page.getHtml().xpath(collectRule.getSubTitleRule()).toString());
            }
            if(collectRule.getPubTimeRule() != null){
                page.putField("publishTime", page.getHtml().xpath(collectRule.getPubTimeRule()).toString());
            }

    //        if (page.getResultItems().get("name") == null) {
    //            //skip this page
    //            page.setSkip(true);
    //        }

            // 部分三：从页面发现后续的url地址来抓取   collectRule.getCollectUrl()
            if (page.getUrl().equals(collectRule.getLinksRule())) {
                log.info("-------------------列表页");
                //添加子页面链接的规则
                page.addTargetRequests(page.getHtml().xpath(collectRule.getLinksRule()).all());
                log.info(collectRule.getLinksRule());
            }
        }catch (Exception e){
            log.error("规则错误，解析失败",e.getMessage());
        }
    }

    @Override
    public void close() throws IOException {

    }
}
