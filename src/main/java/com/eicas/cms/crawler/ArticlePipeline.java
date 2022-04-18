package com.eicas.cms.crawler;

import cn.hutool.core.date.DateUtil;
import com.eicas.cms.pojo.entity.Article;
import com.eicas.cms.service.IArticleService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.time.LocalDateTime;


/**
 * 把PageProcessor抽取的结果，
 * 继续进行处理,保存入库
 */

@Slf4j
@Service
@Setter
@Transactional
public class ArticlePipeline implements Pipeline {

    @Resource
    private IArticleService articleService;

    @Override
    public void process(ResultItems items, Task task) {
        if (!StringUtils.hasText(items.get("title")) || !StringUtils.hasText(items.get("content"))) {
            items.setSkip(true);
            return;
        }

        Long columnId = items.get("columnId");
        String title = items.get("title");
        String subTitle = items.get("subTitle");
        String content = items.get("content");
        String author = items.get("author");
        String essential = items.get("essential");
        String source = items.get("source");
        String publishTimeStr = items.get("publish_time");
        String coverImageUrl = items.get("coverImageUrl");

        /**
         * 作者处理，空则设置为系统采集
         */
        if (!StringUtils.hasText(author)) {
            author = "系统采集";
            log.debug(author);
        }
        /*
         * 摘要处理，为空则摘取内容前200个字符
         */
        if (!StringUtils.hasText(essential)) {
            String str = content.replaceAll("<([^>]*)>", "").trim();
            essential = str.length() >= 200 ? str.substring(200) : str;
            log.debug(essential);
        }
        /*
         * 原文发布时间处理
         */
        LocalDateTime publishTime;
        try {
            publishTime = DateUtil.parse(publishTimeStr).toTimestamp().toLocalDateTime();
        } catch (Exception e) {
            log.debug("无法转换采集到的时间格式");
            publishTime = LocalDateTime.now();
        }

        Article article = new Article();
        article.setColumnId(columnId)
                .setTitle(title)
                .setSubTitle(subTitle)
                .setContent(content)
                .setAuthor(author)
                .setEssential(essential)
                .setSource(source)
                .setCoverImgUrl(coverImageUrl)
                .setPublishTime(publishTime);
        /*
         * 设置文章为待审核
         */
        article.setState(1);

        articleService.saveArticle(article);
        log.debug(article.getContent());
    }
}
