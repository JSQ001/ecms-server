package com.eicas.cms.service;

import com.eicas.cms.pojo.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.vo.ArticleAuditVO;
import com.eicas.cms.pojo.vo.ArticleVO;
import com.eicas.cms.pojo.vo.ArticleStatisticalResults;

import java.time.LocalDateTime;
import java.util.List;


/**
 *
 * 文章信息表 服务类
 *
 * @author jsq
 * @since 2022-03-05
 */
public interface IArticleService extends IService<Article> {

    /**
    * 根据ArticleQueryVo分页查询
    * @param articleQueryVo
    * @return Page<Article>
    */
    Page<Article> listArticles(ArticleVO articleQueryVo);

    /**
    * 保存或更新
    * @param entity
    * @return
    */
    boolean createOrUpdate(Article entity);
    /**
    * 根据id逻辑删除
    * @param id
    * @return
    */
    boolean logicalDeleteById(Long id);
    /**
    * 批量删除文章
    * @param ids
    * @return
    */
    boolean batchDel(List<Long> ids);
    /**
    * 爬虫数据保存
    * @return
    */
    boolean saveCrawl(Article article,String sessionId);

    /**
    * 查询文章统计信息
    * @return
    */
    ArticleStatisticalResults getStatistics(LocalDateTime startTime, LocalDateTime endTime);

    /**
    * 按照栏目统计文章信息
    * @return
    */
    List<ArticleStatisticalResults> getStatisticByColumn(String code, LocalDateTime startTime, LocalDateTime endTime);    /**

     * 批量更新文章
    * @return
    */
    boolean auditArticle(List<ArticleAuditVO> articleList);
}