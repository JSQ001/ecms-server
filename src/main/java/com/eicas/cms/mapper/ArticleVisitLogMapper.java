package com.eicas.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eicas.cms.entity.ArticleVisitLog;
import com.eicas.cms.pojo.param.DayArticleStaticsResult;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author jsq
 * @description 针对表【cms_article_visit_log】的数据库操作Mapper
 * @createDate 2022-04-21 21:51:04
 * @Entity com.eicas.cms.entity.ArticleVisitLog
 */
public interface ArticleVisitLogMapper extends BaseMapper<ArticleVisitLog> {


    /**
     * 统计文章访问记录
     */
    @Select("<script>" +
            "select count(a.id) " +
            "from  cms_article as a, cms_article_visit_log as l " +
            "<where>" +
            "   a.is_deleted = 0 " +
            "   and a.id = l.article_id" +
            "<when test='startTime != null'>" +
            "   and cms_article.publish_time &gt;= #{startTime}" +
            "</when>" +
            "<when test='endTime != null'>" +
            "   and cms_article.publish_time &lt;= #{endTime}" +
            "</when>" +
            "</where>" +
            "</script>"
    )
    Integer statisticLog(LocalDateTime startTime, LocalDateTime endTime);


    /**
     * 统计指定时间范围内每天文章信息
     */
    @Select("<script>" +
            "select date(visit_time) as 'day', count(id) as visitNum \n" +
            "from cms_article_visit_log\n" +
            "where date(visit_time) between #{startTime} and #{endTime}\n" +
            "group by date(visit_time) " +
            "</script>")
    List<DayArticleStaticsResult> staticsArticleByDays(LocalDate startTime, LocalDate endTime);

}




