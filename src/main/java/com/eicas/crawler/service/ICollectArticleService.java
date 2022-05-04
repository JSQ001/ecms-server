package com.eicas.crawler.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.param.ArticleStaticsByCollect;
import com.eicas.cms.pojo.param.CollectArticleParam;
import com.eicas.cms.pojo.vo.ArticleStatisticCompileVO;
import com.eicas.common.ResultData;
import com.eicas.crawler.entity.CollectArticleEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 采集文章信息表 服务类
 *
 * @author osnudt
 * @since 2022-04-21
 */
public interface ICollectArticleService extends IService<CollectArticleEntity> {

    Page<CollectArticleEntity> listCollectArticle(CollectArticleParam param, Integer current, Integer size);

    /**
     * 查询是否存在原文网址为originUrl的文章信息
     * @param originUrl 文章原文网址
     * @return 有返回真，无则返回假
     */
    Boolean hasRepetition(String originUrl);

    List<ArticleStatisticCompileVO> statistic(LocalDateTime startTime, LocalDateTime endTime);

    ResultData<Boolean> batchReceive(List<CollectArticleEntity> list);
}