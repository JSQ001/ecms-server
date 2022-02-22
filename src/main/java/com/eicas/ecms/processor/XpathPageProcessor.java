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
public class XpathPageProcessor implements PageProcessor {


    private String id;
    private final CollectionRuleMapper collectionRuleMapper;

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(5).setSleepTime(5000);

    public XpathPageProcessor(CollectionRuleMapper collectionRuleMapper) {
        this.collectionRuleMapper = collectionRuleMapper;
    }

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {

        CollectionRule collectionRule = collectionRuleMapper.selectById(id);

        //列表页
        String URL_LIST = collectionRule.getInfoPageRule();
        if (page.getUrl().toString().equals(collectionRule.getCollectionPath())) {

            System.out.println("-------------------列表页");
            //把链接的正则添加进去
            page.addTargetRequests(page.getHtml().xpath(URL_LIST).all());
            System.out.println("-------------------列表页1"+URL_LIST);
            System.out.println(page);
        //文章页
        } else {
            System.out.println("-------------------内容页");
            page.putField("source", page.getUrl().toString());
            page.putField("title", page.getHtml().xpath(collectionRule.getTitleRule()).toString());
            page.putField("author", page.getHtml().xpath(collectionRule.getAuthorRule()).toString());
            page.putField("content", page.getHtml().xpath(collectionRule.getContentRule()).toString());
            page.putField("time", page.getHtml().xpath(collectionRule.getPublishTime()).toString());
            page.putField("columnId", collectionRule.getColumnId());
            page.putField("id",collectionRule.getId());
            if (!StringUtils.isBlank(collectionRule.getRemarks())) {
                page.putField("description", page.getHtml().xpath(collectionRule.getRemarks()).toString());
            }
            if (!StringUtils.isBlank(collectionRule.getSubTitle())) {
                page.putField("subtitle", page.getHtml().xpath(collectionRule.getSubTitle()).toString());
            }

        }
    }

    @Override
    public Site getSite() {
        return site;
    }

}