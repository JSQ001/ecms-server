package com.eicas.cms.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.entity.ArticleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eicas.cms.pojo.param.ArticleQueryParam;
import com.eicas.cms.pojo.param.ArticleStaticsResult;
import com.eicas.cms.pojo.param.CatalogArticleStaticsResult;
import com.eicas.cms.pojo.vo.ArticleBriefVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章信息表 Mapper 接口
 *
 * @author osnudt
 * @since 2022-04-19
 */

public interface ArticleMapper extends BaseMapper<ArticleEntity> {

    ArticleBriefVO selectBriefById(Serializable id);


    /**
     * 根据文章状态统计文章相关信息 0-草稿,1-待审核,2-审核通过,3-审核不通过
     * */
    @Select("<script>" +
            "select count(case when id is not null then 0 END) as allNum, " +
            "       count(case when state = 0 then 0 end) as draftNum, " +
            "       count(case when state = 1 then 0 end) as notAuditNum, " +
            "       count(case when state = 2 then 0 end) as approvedNum, " +
            "       count(case when state = 3 then 0 end) as rejectNum " +
            " from  cms_article " +
            "<where>" +
            "   is_deleted = 0 " +
            "<when test='startTime != null'>" +
            "   and cms_article.publish_time &gt;= #{startTime}" +
            "</when>"+
            "<when test='endTime != null'>" +
            "   and cms_article.publish_time &lt;= #{endTime}" +
            "</when>"+
            "</where>" +
            "</script>"
    )
    ArticleStaticsResult statisticArticleInfo(LocalDateTime startTime, LocalDateTime endTime);


    /**
     * 统计时间段内文章含访问量
     * */
    @Select("<script>" +
            "select count(case when t.article_log_id then 0 end) as visitLogNum,\n" +
            "\t\t\t count(distinct(id)) as allNum,\n" +
            "\t\t\t count(case when state = 0 then 0 end) as draftNum,\n" +
            "\t\t\t count(case when state = 1 then 0 end) as notAuditNum,\n" +
            "       count(case when state = 2 then 0 end) as approvedNum,\n" +
            "       count(case when state = 3 then 0 end) as rejectNum\n" +
            "from (\n" +
            "\tselect a.id, a.state, a.content, l.id as article_log_id\n" +
            "\tfrom cms_article as a \n" +
            "\tleft join cms_article_visit_log as l\n" +
            "\ton a.id = l.article_id\n" +
            "<where>" +
            "   a.is_deleted = 0 " +
            "<when test='startTime != null'>" +
            "   and a.publish_time &gt;= #{startTime}" +
            "</when>"+
            "<when test='endTime != null'>" +
            "   and a.publish_time &lt;= #{endTime}" +
            "</when>"+
            "</where>" +
            ") t" +
            "</script>"
    )
    ArticleStaticsResult statisticArticleInfoAndVisit(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 条件查询文章信息
     * @Param
     */
    @Select("<script>" +
            "select a.id, a.catalog_id, a.title, a.sub_title, a.keyword, a.essential, a.content, a.state, a.hit_nums,a.publish_time," +
            "a.is_visible as visible, a.is_focus as focus, a.is_recommended as recommended, a.is_top as top, a.sort_order, " +
            "a.cover_img_url, a.link_url, a.type, a.author, a.origin_url, a.source, a.remarks, c.catalog_name as catalogName from \n" +
            "cms_article as a, cms_catalog as c\n" +
            "<where>" +
            "   a.is_deleted = 0 and c.is_deleted = 0 \t\n" +
            "   and c.id = a.catalog_id\t\n" +
            "<if test='param.title != null and param.title!=\"\"'>" +
            "   and a.title like CONCAT('%',#{param.title},'%')" +
            "</if> " +
            "<if test='param.state != null'>" +
            "   and a.state = #{param.state}" +
            "</if> " +
            "<if test='param.visible != null'>" +
            "   and a.is_visible = #{param.visible}" +
            "</if> " +
            "<if test='param.focus != null'>" +
            "   and a.is_focus = #{param.focus}" +
            "</if> " +
            "<if test='param.recommended != null'>" +
            "   and a.is_recommended = #{param.recommended}" +
            "</if> " +
            "<if test='param.top != null'>" +
            "   and a.is_top = #{param.top}" +
            "</if> " +
            "<if test='param.beginPublishTime != null'>" +
            "   and a.publish_time &gt;= #{param.beginPublishTime}" +
            "</if> " +
            "<if test='param.endPublishTime != null'>" +
            "   and a.publish_time &lt;= #{param.endPublishTime}" +
            "</if> " +
            "<if test='param.catalogId != null'>" +
            "   and c.id in (" +
            "       select id from cms_catalog as c1, (select tree_rel from cms_catalog where id = #{param.catalogId}) t\n" +
            "       where c1.tree_rel LIKE concat(t.tree_rel,'%')" +
            "   )" +
            "</if> " +
            "</where>" +
            "ORDER BY a.sort_order, a.publish_time desc" +
            "</script>"
    )
    Page<ArticleEntity> listArticle(ArticleQueryParam param, Page<ArticleEntity> page);

    /**
     * 统计指定栏目的子栏目文章信息、访问信息
     * */
    @Select("<script>" +
            "select t.catalog_name,\n" +
            " count(case when visit_log_id is not null then 0 END) as visitLogNum,\n" +
            " count(case when id is not null then 0 END) as 'allNum',\n" +
            " count(case when state = 0 then 0 end) as draftNum, \n" +
            " count(case when state = 1 then 0 end) as notAuditNum,\n" +
            " count(case when state = 2 then 0 end) as approvedNum,\n" +
            " count(case when state = 3 then 0 end) as rejectNum \n" +
            " from (\n" +
            "\tselect t.*, l.id as visit_log_id\n" +
            "\tfrom (\n" +
            "\t\tselect c.catalog_name,state,a.id \n" +
            "\t\tfrom cms_article as a, cms_catalog as c\n" +
            "           <where>" +
            "               c.id = a.catalog_id " +
            "               and a.is_deleted = 0 " +
            "               <when test='startTime != null'>" +
            "                   and a.publish_time &gt;= #{startTime}" +
            "               </when>"+
            "               <when test='startTime != null'>" +
            "                   and a.publish_time &lt;= #{endTime}" +
            "               </when>"+
            "               and c.id in (select id from cms_catalog  where parent_id = (select id from cms_catalog where code = #{code}))\n" +
            "           </where>" +
            "\t) t left join cms_article_visit_log as l on l.article_id = t.id\n" +
            ") t\n" +
            "group by  t.catalog_name" +
            "</script>")
    List<CatalogArticleStaticsResult> staticsCatalogArticle(@Param("code") String code, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据栏目code查询文章信息
     * @param param
     * @param page
     * @return Page<ArticleEntity>
     * */
    @Select("" +
            "<script>" +
            "select * from cms_article" +
            "<where>" +
            "   is_deleted = 0\n" +
            "   <if test='param.state != null' >" +
            "       and state = #{param.state}" +
            "   </if> " +
            "   <if test='param.code != null and param.code!=\"\"'>" +
            "       and catalog_id  in (\n" +
            "           select id from cms_catalog where code = #{param.code}\n" +
            "       )\n" +
            "   </if> " +
            "</where>" +
            "order by sort_order, publish_time desc" +
            "</script>"
    )
    Page<ArticleEntity> listArticleByCatalogCode(ArticleQueryParam param, Page<ArticleEntity> page);
}
