package com.eicas.cms.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eicas.cms.pojo.vo.ArticleAuditVO;
import com.eicas.cms.pojo.vo.ArticleVO;
import com.eicas.cms.pojo.vo.ArticleStatisticalResults;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 文章信息表 Mapper 接口
 * </p>
 *
 * @author jsq
 * @since 2022-03-05
 */
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 分页条件查询article
     * */
    @Select("<script>" +
            "select a.id,column_id,c.name as columnName, title, sub_title, a.sort_order, a.keyword, content, essential, is_top, is_major,a.publish_time," +
                    "a.cover_img_url, link_url, a.type, author, source, state, is_show, is_focus, is_recommended,hit_nums " +
            "from cms_article as a, cms_column as c" +
            "<where>" +
            "   column_id = c.id and a.is_deleted = 0" +
            "<if test='param.columnId != null and param.columnId!=\"\"'>" +
            "   and column_id = #{param.columnId}" +
            "</if>" +
            "<if test='param.state != null and param.state!=\"\"'>" +
            "   and state = #{param.state}" +
            "</if>" +
            "<when test='param.startTime != null'>" +
            "   and a.publish_time &gt;= #{param.startTime}" +
            "</when>"+
            "<when test='param.endTime != null'>" +
            "   and a.publish_time &lt;= #{param.endTime}" +
            "</when>"+
            "<when test='param.title != null and param.title!=\"\" '>" +
            "   and title like concat('%',#{param.title, jdbcType=VARCHAR},'%')" +
            "</when>" +
            "</where>" +
            " ORDER BY sort_order, a.publish_time desc, a.updated_time desc" +
            "</script>")
    Page<Article> listArticles(@Param("param") ArticleVO param, Page page);

    /**
     * 统计文章信息
     * */
    @Select("<script>" +
            "select count(case when id is not null then 0 END) as 'all', " +
            "       count(case when state = 0 then 0 end) as eidt, " +
            "       count(case when state = 1 then 0 end) as notApproved, " +
            "       count(case when state = 2 then 0 end) as approved, " +
            "       count(case when state = 3 then 0 end) as reject " +
            " from  cms_article " +
            "<where>" +
            "<when test='startTime != null'>" +
            "   and cms_article.publish_time &gt;= #{startTime}" +
            "</when>"+
            "<when test='endTime != null'>" +
            "   and cms_article.publish_time &lt;= #{endTime}" +
            "</when>"+
            "</where>" +
            "</script>")
    ArticleStatisticalResults statistics(@Param("startTime")LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 按栏目统计文章信息
     * */
    @Select("<script>" +
            "select column_id as columnId, c.name,\n" +
            "count(case when column_id is not null then 0 END) as 'all', \n" +
            "count(case when state = 0 then 0 end) as editing, count(case when state = 1 then 0 end) as notApproved, \n" +
            "count(case when state = 2 then 0 end) as approved,\n" +
            "count(case when state = 3 then 0 end) as reject \n" +
            "from cms_article as a,cms_column as c\n" +
            "<where>" +
            "   c.id = a.column_id " +
            "   <when test='startTime != null'>" +
    "               and cms_article.publish_time &gt;= #{startTime}" +
    "           </when>"+
    "           <when test='startTime != null'>" +
"                   and cms_article.publish_time &lt;= #{endTime}" +
    "           </when>"+
            "   and c.id in (select id from cms_column  where parent_id = (select id from cms_column where code = #{code}))\n" +
            "</where>" +
            "group by column_id "+
            "</script>")
    List<ArticleStatisticalResults> statisticByColumn(@Param("code") String code, @Param("startTime")LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 审核文章接口
     * */
    boolean auditArticle(@Param("list") List<ArticleAuditVO> list);
}

