package com.eicas.cms.crawling;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.eicas.cms.pojo.entity.Article;
import com.eicas.cms.service.IArticleService;
import com.eicas.cms.utils.DownloadUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;


/**
 * 把PageProcessor抽取的结果，
 * 继续进行处理,保存入库
 *
 * */

@Slf4j
@Service
@Setter
@Transactional
public class StorePipeline implements Pipeline {

    @Resource
    private IArticleService iArticleService;

    @Value("${filePath.image}")
    private String imagePath;

    @Value("${filePath.imageMappingPath}")
    private String imageMappingPath;

    private String sessionId;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Article article = new Article()
                .setColumnId(resultItems.get("columnId"))
                .setTitle(resultItems.get("title"))
                .setAuthor(resultItems.get("author"))
                .setSource(resultItems.get("source"))
                .setContent(resultItems.get("content"))
                .setEssential(resultItems.get("essential"))
                .setRemarks(resultItems.get("id").toString())
                .setSubTitle(resultItems.get("subTitle"));

        try{
            DateTime publishTime = DateUtil.parse(resultItems.get("publishTime"));
            article.setPublishTime(publishTime.toLocalDateTime());
        }catch (Exception e){
            log.error("发布时间规则错误"+e.getMessage());
        }

        //将正文中的图片全部保存，取第一张做为文章标题图片
        String content = article.getContent();
        if(StringUtils.hasText(content)){
            Document document;
            try {
                document = Jsoup.parse(content);
                Elements imgList = document.select("img[src]");

                log.info("图片地址：" + imgList);
                // 遍历节点，查找图片并保存
                byte count = 0;
                for (Element img : imgList) {
                    String url = img.attr("abs:src");
                    String[] names = url.split("/");
                    String name = names[names.length-1];
                    try {
                        String urlFile = DownloadUtil.download(url,name,imagePath,imageMappingPath);
                        if(urlFile != null){
                            img.attr("src",urlFile);
                            if(count++ == 0){
                                // 将第一张图片设为焦点图
                                article.setCoverImgUrl(urlFile);
                                article.setIsFocus(true);
                            }
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
                article.setContent(document.body().toString());
            }catch (Exception e){
                log.error("解析错误");
            }
        }
        iArticleService.saveCrawl(article,sessionId);
    }

}
