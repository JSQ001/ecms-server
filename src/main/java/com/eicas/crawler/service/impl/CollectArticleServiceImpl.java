package com.eicas.crawler.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.entity.ArticleEntity;
import com.eicas.cms.pojo.param.ArticleStaticsByCollect;
import com.eicas.cms.pojo.param.CollectArticleParam;
import com.eicas.cms.pojo.vo.ArticleStatisticCompileVO;
import com.eicas.cms.service.IArticleService;
import com.eicas.common.ResultData;
import com.eicas.crawler.entity.CollectArticleEntity;
import com.eicas.crawler.mapper.CollectArticleMapper;
import com.eicas.crawler.service.ICollectArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 采集文章信息表 服务实现类
 *
 * @author osnudt
 * @since 2022-04-21
 */
@Service
public class CollectArticleServiceImpl extends ServiceImpl<CollectArticleMapper, CollectArticleEntity> implements ICollectArticleService {
    @Resource
    CollectArticleMapper collectArticleMapper;

    @Resource
    IArticleService articleService;

    @Override
    public Page<CollectArticleEntity> listCollectArticle(CollectArticleParam param, Integer current, Integer size) {
        return collectArticleMapper.listCollectArticle(param, Page.of(current,size));
    }

    @Override
    public Boolean hasRepetition(String originUrl) {
        return !collectArticleMapper.selectList(
                Wrappers.<CollectArticleEntity>lambdaQuery()
                        .eq(CollectArticleEntity::getOriginUrl, originUrl)).isEmpty();
    }

    @Override
    public List<ArticleStatisticCompileVO> statistic(LocalDateTime startTime, LocalDateTime endTime) {
        return collectArticleMapper.statistic(startTime, endTime);
    }

    @Override
    public ResultData batchReceive(List<CollectArticleEntity> list) {
        if(!collectArticleMapper.batchReceive(list)){
            ResultData.failed("状态更新不成功，入库失败！");
        }
        List<ArticleEntity> articleEntityList = list.stream().map(
                i-> new ArticleEntity()
                    .setCatalogId(i.getCatalogId())
                    .setTitle(i.getTitle())
                    .setSubTitle(i.getSubTitle())
                    .setKeyword(i.getKeyword())
                    .setContent(i.getContent())
                    .setEssential(i.getEssential())
                    .setCoverImgUrl(i.getCoverImgUrl())
                    .setAuthor(i.getAuthor())
                    .setOriginUrl(i.getOriginUrl())
                    .setPublishTime(i.getPublishTime())
                    .setSource(i.getSource())
                    .setState(2)
        ).collect(Collectors.toList());
        if(!articleService.saveBatch(articleEntityList)){
            ResultData.failed("入库失败！");
        }
        return ResultData.success("入库成功！");
    }
}
