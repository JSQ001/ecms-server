package com.eicas.crawler.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.param.CollectArticleParam;
import com.eicas.cms.pojo.vo.ArticleStatisticCompileVO;
import com.eicas.crawler.entity.CollectArticleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 采集文章信息表 Mapper 接口
 * </p>
 *
 * @author osnudt
 * @since 2022-04-21
 */
public interface CollectArticleMapper extends BaseMapper<CollectArticleEntity> {


    /**
     * 根据文节点统计采集文章数
     * */
    @Select("<script>" +
            "\tselect count(a.id) as allNum, n.name as collectNodeName\n" +
            "\tfrom cms_collect_article as a, cms_collect_node as n\n" +
            "<where>" +
            "   a.is_deleted = 0 and n.is_deleted = 0 and a.catalog_id = n.catalog_id" +
            "<when test='startTime != null'>" +
            "   and a.publish_time &gt;= #{startTime}" +
            "</when>"+
            "<when test='endTime != null'>" +
            "   and a.publish_time &lt;= #{endTime}" +
            "</when>"+
            "</where>" +
            "\tgroup by collectNodeName" +
            "</script>"
    )
    List<ArticleStatisticCompileVO> statistic(LocalDateTime startTime, LocalDateTime endTime);


    /**
     * 条件查询采集文章信息
     * @Param
     */
    @Select("<script>" +
            "select a.id, a.catalog_id, c.catalog_name as catalogName, a.title, a.sub_title, a.keyword, a.essential, a.content, a.publish_time," +
            "a.cover_img_url, a.author, a.origin_url, a.source, a.collect_time, a.is_received as received, c.catalog_name as catalogName from \n" +
            "cms_collect_article as a, cms_catalog as c\n" +
            "<where>" +
            "   a.is_deleted = 0 and c.is_deleted = 0 \t\n" +
            "   and c.id = a.catalog_id\t\n" +
            "<if test='param.title != null and param.title!=\"\"'>" +
            "   and a.title like CONCAT('%',#{param.title},'%')" +
            "</if> " +
            "<if test='param.received != null'>" +
            "   and a.is_received = #{param.received}" +
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
            "ORDER BY a.publish_time desc" +
            "</script>"
    )
    Page<CollectArticleEntity> listCollectArticle(CollectArticleParam param, Page<CollectArticleEntity> page);


    @Update("<script>" +
            "update cms_collect_article set is_received = 1 where id in " +
            "  <foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">\n" +
            "      #{item.id}\n" +
            "  </foreach> " +
            "</script>"

    )
    boolean batchReceive(List<CollectArticleEntity> list);
}
