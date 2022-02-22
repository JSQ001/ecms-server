package com.eicas.ecms.pipeline;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eicas.ecms.component.WebSocketServer;
import com.eicas.ecms.entity.ArticleRule;
import com.eicas.ecms.entity.CollectionRule;
import com.eicas.ecms.entity.Ecms;
import com.eicas.ecms.mapper.EcmsMapper;
import com.eicas.ecms.mapper.ArticleRuleMapper;
import com.eicas.ecms.mapper.CollectionRuleMapper;
import com.eicas.ecms.pojo.SessionMapPOJO;
import com.eicas.ecms.utils.DownloadUtil;
import com.eicas.ecms.utils.StringDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class ZZPipeline implements Pipeline {

    //存储地址
    @Value("${file.path}")
    private String filePath;

    //nginx路径地址
    @Value("${file.nginxPath}")
    private String nginxPath;


    @Resource
    private final EcmsMapper ecmsMapper;
    private final CollectionRuleMapper collectionRuleMapper;
    private final ArticleRuleMapper articleRuleMapper;

    @Autowired
    public ZZPipeline(EcmsMapper ecmsMapper, CollectionRuleMapper collectionRuleMapper, ArticleRuleMapper articleRuleMapper) {
        this.ecmsMapper = ecmsMapper;
        this.collectionRuleMapper = collectionRuleMapper;
        this.articleRuleMapper = articleRuleMapper;
    }

    ConcurrentHashMap<String, String> mp = SessionMapPOJO.getInstance().getMp();


    @Override
    public void process(ResultItems resultItems, Task task)  {

        Ecms ecms = new Ecms();
        ArticleRule articleRule = new ArticleRule();
        //获取爬取内容
        ecms.setAuthor(resultItems.get("author"));
        ecms.setColumnId(resultItems.get("columnId"));
        ecms.setContent(resultItems.get("content"));
        ecms.setTitle(resultItems.get("title"));
        ecms.setSource(resultItems.get("source"));
        if (!StringUtils.isBlank(resultItems.get("description"))) {
            ecms.setRemarks(resultItems.get("description"));
        }
        if (!StringUtils.isBlank(resultItems.get("subtitle"))) {
            ecms.setSubTitle(resultItems.get("subtitle"));
        }
        String time = resultItems.get("time");
        //解析正文
        if (!StringUtils.isBlank(time)){
            time = StringDateUtils.StringData(time);

            DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ecms.setCreatedTime(LocalDateTime.parse(time,dateTimeFormatter1));
            CollectionRule collectionRule = collectionRuleMapper.selectById(resultItems.get("id"));
            //下载图片
            Document document =Jsoup.parse(ecms.getContent());
            log.info("要有图片了");
            Elements imgs = document.select("img[src]");
            System.out.println(imgs);
            List<String> list = new ArrayList<>();
            for (Element img : imgs) {
                String url = img.attr("src");
                if (!StringUtils.isBlank(collectionRule.getImgAddressPrefix())){
                    url = collectionRule.getImgAddressPrefix()+url;
                }
                String[] names = url.split("/");
                String name = names[names.length-1];
                try {
                    String urlFile = DownloadUtil.download(url,name,filePath,nginxPath);
                    list.add(urlFile);
                    img.attr("src",urlFile);
                } catch (Exception e) {
                    log.error("图片下载失败",e);
                }
            }
            if (list.size()!=0) {
                ecms.setImg(list.get(0));
            }
            ecms.setContent(document.body().toString());
            ecms.setStatus(0);

            articleRule.setArticleId(ecms.getId());
            articleRule.setImgNum(list.size());
            articleRule.setColumnId(ecms.getColumnId());
            articleRule.setRuleId(collectionRule.getId());
            articleRule.setCreatedTime(StringDateUtils.Now());
            articleRule.setStatus(ecms.getStatus());

            QueryWrapper<Ecms> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("author",ecms.getAuthor())
                    .eq("title",ecms.getTitle())
                    .eq("content",ecms.getContent());
            //去重入库
            if (0 == ecmsMapper.selectNum(ecms)){
                int insertAffect = ecmsMapper.insert(ecms);
                articleRule.setArticleId(ecmsMapper.selectOne(queryWrapper).getId());
                articleRuleMapper.insert(articleRule);
            } else {
                System.out.println("文章已存在");
            }
        }
    }




}
