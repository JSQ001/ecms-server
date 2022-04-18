package com.eicas.cms.crawler;

import com.eicas.cms.pojo.entity.CollectRule;
import com.eicas.cms.service.ICollectRuleService;
import com.eicas.cms.utils.FileUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.Resource;
import java.io.Closeable;
import java.net.URI;
import java.util.List;

/**
 * PageProcessor的定制分为三个部分，分别是爬虫的配置、页面元素的抽取和链接的发现。
 */
@Component
@Getter
@Setter
@Slf4j
public class ArticlePageProcessor implements PageProcessor, Closeable {

    @Resource
    ICollectRuleService collectRuleService;

    @Value("${filePath.image}")
    private String imagePath;

    @Value("${filePath.imageMappingPath}")
    private String imageMapping;

    private CollectRule collectRule;

    private Site site = Site.me().setRetryTimes(3).setSleepTime(5000);

    /**
     * 定制爬虫抽取逻辑，校验采集规则
     *
     * @param page 采集的页面
     */
    @Override
    public void process(Page page) {
        String pattern_list = collectRule.getLinksRule();
        String pattern_title = collectRule.getTitleRule();
        String pattern_content = collectRule.getContentRule();
        String pattern_subtitle = collectRule.getSubTitleRule();
        String pattern_essential = collectRule.getEssentialRule();
        String pattern_author = collectRule.getAuthorRule();
        String pattern_publish_time = collectRule.getAuthorRule();
        /*
         * 校验规则，列表、标题、内容规则不能为空
         */
        if (collectRule == null ||
                !StringUtils.hasText(pattern_list) ||
                !StringUtils.hasText(pattern_title) ||
                !StringUtils.hasText(pattern_content)) {
            page.setSkip(true);
            return;
        }

        if (page.getUrl().toString().equals(collectRule.getCollectUrl())) {
            log.info("开始分析列表页");
            List<String> urls = page.getHtml().xpath(pattern_list).links().all();
            page.addTargetRequests(urls);
            log.info("如下地址将被采集：" + urls.toString());
        } else {
            log.info("开始采集内容页");
            /*
             * 设置入库栏目ID
             */
            page.putField("columnId", collectRule.getColumnId());

            /*
             * 采集文章标题
             */
            page.putField("title", page.getHtml().xpath(pattern_title).toString().trim());
            /*
             * 设置文章来源地址URL
             */
            page.putField("source", page.getUrl().toString().trim());

            /*
             * 采集文章副标题
             */
            if (StringUtils.hasText(pattern_subtitle)) {
                page.putField("subTitle", page.getHtml().xpath(pattern_subtitle).toString().trim());
            } else {
                page.putField("subTitle", "");
            }
            /*
             * 采集文章摘要
             */
            if (StringUtils.hasText(pattern_essential)) {
                page.putField("essential", page.getHtml().xpath(pattern_essential).toString().trim());
            } else {
                page.putField("essential", "");
            }
            /*
             * 采集文章作者
             */
            if (StringUtils.hasText(pattern_author)) {
                page.putField("author", page.getHtml().xpath(pattern_author).toString().trim());
            } else {
                page.putField("autor", "");
            }

            /*
             * 采集文章发布时间
             */
            if (StringUtils.hasText(pattern_publish_time)) {
                page.putField("publish_time", page.getHtml().xpath(pattern_publish_time).toString().trim());
            }

            /*
             * 1.采集文章内容
             * 2.下载正文图片，更新图片链接
             * 3.将第一张图片设置为文章封面图片
             */
            StringBuffer content = new StringBuffer(page.getHtml().xpath(pattern_content).toString().trim());
            String coverImageUrl = crabImages(page.getUrl().toString(), content);
            if (StringUtils.hasText(coverImageUrl)) {
                page.putField("coverImageUrl", coverImageUrl.trim());
            }
            page.putField("content", content.toString().trim());
        }
    }

    /**
     * 1.下载文章所有图片，保存至本地
     * 2.更新文章图片地址
     * 3.返回第1张图片URL，将被作为文章标题图片
     *
     * @param baseURI 文章URL
     * @param content 文章内容
     * @return 更新图片链接后的文章内容
     */

    private String crabImages(String baseURI, StringBuffer content) {
        String coverImageUrl = null;
        // 清除内嵌样式及脚本
        content.replace(0, content.length()-1, stripHTML(content.toString()));
        Document document;
        if (StringUtils.hasText(content)) {
            try {
                // 基本网页URI
                URI base = new URI(baseURI);
                int count = 0;
                document = Jsoup.parse(content.toString()).normalise();
                // 清除style样式
                document.select("*").removeAttr("style");
                for (Element element : document.getElementsByTag("img")) {
                    String propertyValue = element.attr("src");
                    if (!propertyValue.matches("^(https?|ftp):(\\\\|//).*$")) {
                        // 解析相对URL，得到绝对URI
                        String imageUrl = base.resolve(propertyValue).toString();

                        String[] names = imageUrl.split("/");
                        String filename = names[names.length - 1];
                        String urlFile = FileUtils.download(imageUrl, filename, imagePath, imageMapping);

                        if (urlFile != null) {
                            element.attr("src", urlFile);
                            // 将第一张图片设为焦点图
                            if (count++ == 0) {
                                coverImageUrl = urlFile;
                            }
                        }
                    }
                }
                content.replace(0, content.length()-1, document.body().toString());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return coverImageUrl;
    }

    /**
     * 清除自带样式及JS脚本
     */
    private String stripHTML(String html) {
        /*
         * 仅保留br,p,img,div标签
         */
        String cleanStr = html.trim()
                .replace("\r\n", "")
                .replace("\r", "")
                .replace("\n", "")
                .replace("&nbsp", "");
        return cleanStr;
    }

    @Override
    public void close() {
        collectRuleService.updateCollectStatusById(collectRule.getId(), 0L);
    }
}
