package com.eicas.cms.crawling;

//import com.eicas.cms.component.MyWebsocketServer;
import com.eicas.cms.pojo.entity.CollectRule;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.Arrays;

/**
* PageProcessor的定制分为三个部分，分别是爬虫的配置、页面元素的抽取和链接的发现。
* */
@Component
@Getter
@Setter
@Slf4j
public class XPathPageProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(5).setSleepTime(5000);

    //采集规则
    private CollectRule collectRule;

    //websocketSessionId
    private String sessionId;

    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑

    @Override
    public void process(Page page) {
        try{
            // 部分二：定义如何抽取页面信息，并保存下来
            page.putField("source", page.getUrl().toString());
            page.putField("title", page.getHtml().xpath(collectRule.getTitleRule()).toString());

            page.putField("content", page.getHtml().xpath(collectRule.getContentRule()).toString());
            page.putField("columnId", collectRule.getColumnId());
            if(StringUtils.hasText(collectRule.getSubTitleRule())){
                page.putField("subTitle", page.getHtml().xpath(collectRule.getSubTitleRule()).toString());
            }

            if(StringUtils.hasText(collectRule.getEssentialRule())){
                //page.getHtml().xpath(collectRule.getContentRule()).toString()
                if (page.getHtml().xpath(collectRule.getEssentialRule()).toString().equals("")){
                    String temp=page.getHtml().xpath(collectRule.getContentRule()).toString().substring(0,100);

                    temp=temp.replace(temp,"/<[^>]+/g,");
                    page.putField("essential", temp);
                }
                else
                page.putField("essential", page.getHtml().xpath(collectRule.getEssentialRule()).toString());
            }
            if(StringUtils.hasText(collectRule.getAuthorRule())){
                page.putField("author", page.getHtml().xpath(collectRule.getAuthorRule()).toString());
            }
            if(StringUtils.hasText(collectRule.getPubTimeRule())){
                page.putField("publishTime", page.getHtml().xpath(collectRule.getPubTimeRule()).toString());
            }
            page.putField("id", collectRule.getId());

            //        if (page.getResultItems().get("name") == null) {
    //            //skip this page
    //            page.setSkip(true);
    //        }

            // 部分三：从页面发现后续的url地址来抓取 ，
            if(StringUtils.hasText(collectRule.getLinksRule())) {
                String[] links = collectRule.getLinksRule().split("\n");
                Arrays.stream(links).forEach(i->{
                    log.info("链接------------：" + i.trim());
                    Selectable link = page.getHtml().xpath(i.trim());
                    if(link.nodes().size() > 0){
                        //添加子页面链接的规则
                        page.addTargetRequests(link.all());
                        int size = link.nodes().size();
                        //MyWebsocketServer.pageQueue.put(sessionId,MyWebsocketServer.pageQueue.get(sessionId)+size);
                    }
                });
            }
        }catch (Exception e){
            log.error("规则错误，解析失败: "+e.getMessage());
        }
    }

}
