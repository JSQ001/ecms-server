package com.eicas.cms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.cms.entity.ArticleEntity;
import com.eicas.cms.entity.ArticleVisitLog;
import com.eicas.cms.pojo.param.DayArticleStaticsResult;
import com.eicas.cms.service.ArticleVisitLogService;
import com.eicas.cms.mapper.ArticleVisitLogMapper;
import com.eicas.cms.service.IArticleService;
import lombok.var;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author jsq
* @description 针对表【cms_article_visit_log】的数据库操作Service实现
* @createDate 2022-04-21 21:51:04
*/
@Service
public class ArticleVisitLogServiceImpl extends ServiceImpl<ArticleVisitLogMapper, ArticleVisitLog> implements ArticleVisitLogService{

    @Resource
    private ArticleVisitLogMapper articleVisitLogMapper;

    @Resource
    private IArticleService articleService;

    @Override
    public Integer statisticLog(LocalDateTime startTime, LocalDateTime endTime) {
        return articleVisitLogMapper.statisticLog(startTime, endTime);
    }

    @Override
    public List<DayArticleStaticsResult> staticsArticleByDays(LocalDate startTime, LocalDate endTime) {
        List<DayArticleStaticsResult> list = articleVisitLogMapper.staticsArticleByDays(startTime,endTime);
        List<LocalDate> days = list.stream().map(DayArticleStaticsResult::getDay).collect(Collectors.toList());

        for(LocalDate i = startTime; !i.isAfter(endTime); i = i.plusDays(1)){
            if(!days.contains(i)){
                DayArticleStaticsResult result = new DayArticleStaticsResult();
                result.setDay(i);
                result.setVisitNum(0);
                list.add(result);
            }
        }
        list = list.stream().sorted((a,b)-> {
            System.out.println(a);
            System.out.println(b);
            int i = a.getDay().isBefore(b.getDay()) ? -1 : 0;
            return a.getDay().isBefore(b.getDay()) ? -1 : 0;
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public boolean save(ArticleVisitLog entity) {
        if(super.save(entity)){
            ArticleEntity article = articleService.getById(entity.getArticleId());
            article.setHitNums(article.getHitNums() == null ? 1 : article.getHitNums()+1);
            return articleService.updateById(article);
        }
        return false;
    }
}




