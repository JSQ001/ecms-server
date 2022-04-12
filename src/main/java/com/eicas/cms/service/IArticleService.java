package com.eicas.cms.service;

import com.eicas.cms.pojo.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.vo.*;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


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
    Map getStatistics(String monthTime,LocalDateTime startTime, LocalDateTime endTime);

    /**
    * 按照栏目统计文章信息
    * @return
    */
    List<StatisticalResults> getStatisticByColumn(String columnId, LocalDateTime startTime, LocalDateTime endTime);    /**

     * 批量更新文章
    * @return
    */
    boolean auditArticle(ArticleAuditVO articleAuditVO);

    /**
    * 修改热点新闻
    */
    boolean modifyFocus(Long id, boolean isFocus);

    /**
    *自动采集重复查询
    * */
    int listCount(Map paramMap);

    /**文章点击次数*/
    int articlePoint(long id);


    /**
     * 用户发表文章统计
     */
    Map statisticsByUser(Map parmaMap);


    /**
     * 统计自动采集节点发布信息
     * */
    List<StatisticalResults> statisticsByPointName(Map parmaMap);



    /**
     * 统计某栏目的发布信息
     * */
    List<Article> statisticByColumnArticle(@Param("state") String state, @Param("columnId")String columnId, @Param("startTime")LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);


    /**
     * 统计某时间段栏目发布信息浏览总量
     * */

   Map statisticsByHitNumsCount(Map parmaMap);




    /**
     * 某栏目总浏览量前8
     *
     * */
    List<StatisticalResults> statisticsByHitNumsCountBefore(Map parmaMap);




}