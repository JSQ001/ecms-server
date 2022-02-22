package com.eicas.ecms.processor;

import com.eicas.ecms.entity.CollectionRule;
import com.eicas.ecms.mapper.CollectionRuleMapper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Data
@Component
public class ZZPageProcessor implements PageProcessor {

    private String id;
    private final CollectionRuleMapper collectionRuleMapper;

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(2).setSleepTime(1000);

    public ZZPageProcessor(CollectionRuleMapper collectionRuleMapper) {
        this.collectionRuleMapper = collectionRuleMapper;
    }

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
//列表页
        CollectionRule collectionRule = collectionRuleMapper.selectById(id);
        String URL_LIST = collectionRule.getInfoPageRule();
        if (page.getUrl().toString().equals(collectionRule.getCollectionPath())) {
            System.out.println("-------------------列表页");
//            page.addTargetRequests(page.getHtml().xpath("//div[@class=\"articleList\"]").links().regex(URL_POST).all());
            page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
            //文章页
        } else {
            System.out.println("内容页");
            page.putField("title", page.getHtml().regex(collectionRule.getTitleRule()).toString());
            page.putField("author", page.getHtml().regex(collectionRule.getAuthorRule()).toString());
            page.putField("content", page.getHtml().regex(collectionRule.getContentRule()).toString());
            page.putField("time", page.getHtml().regex(collectionRule.getPublishTime()).toString());
            page.putField("columnId", collectionRule.getColumnId());
            page.putField("id",collectionRule.getId());
            if (StringUtils.isBlank(collectionRule.getRemarks())) {
                page.putField("description", page.getHtml().regex(collectionRule.getRemarks()).toString());
            }
            if (StringUtils.isBlank(collectionRule.getSubTitle())) {
                page.putField("subtitle", page.getHtml().regex(collectionRule.getSubTitle()).toString());
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

}