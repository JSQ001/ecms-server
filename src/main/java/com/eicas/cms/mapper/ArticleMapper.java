package com.eicas.cms.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eicas.cms.pojo.vo.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
            "select a.id,a.column_id,c.name as columnName, title, sub_title, a.sort_order, a.keyword, a.content, a.essential, a.is_top, a.is_major,a.publish_time," +
                    "a.cover_img_url, a.link_url, a.type, a.author, a.source, a.state, a.is_show, a.is_focus, a.is_recommended,a.hit_nums,a.is_major,a.is_notice " +
            "from cms_article as a " +
            "left join cms_column as c on c.id = a.column_id " +
            "<where>" +
            "   a.is_deleted = 0" +
            "<if test='ids != null'>" +
            "  and a.column_id in " +
            "  <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">\n" +
            "      #{item}\n" +
            "  </foreach> " +
            "</if>" +
            "<if test='param.state != null and param.state!=\"\"'>" +
            "   <choose>\n" +
            "      <when test='param.state == 5'>\n" +
            "           and a.state in  (2,3)" +
            "      </when>\n" +
            "      <otherwise>\n" +
            "           and a.state = #{param.state} " +
            "      </otherwise>\n" +
            "   </choose>  " +
            "</if> " +
            "<if test='param.isFocus != null and param.isFocus !=\"\"'>" +
            "   and a.is_focus = #{param.isFocus}" +
            "</if>  " +
            "<if test='param.isTop != null and param.isTop !=\"\"'>" +
            "   and a.is_top = #{param.isTop}" +
            "</if>  " +
            "<when test='param.startTime != null'>" +
            "   and a.publish_time &gt;= #{param.startTime}" +
            "</when>"+
            "<when test='param.endTime != null'>" +
            "   and a.publish_time &lt;= #{param.endTime}" +
            "</when>"+
            "<when test='param.title != null and param.title!=\"\" '>" +
            "   and a.title like concat('%',#{param.title, jdbcType=VARCHAR},'%')" +
            "</when>" +
            "</where>" +
            " ORDER BY  a.publish_time desc, a.updated_time desc" +
            "</script>")
    Page<Article> listArticles(@Param("ids") List<Long> ids, @Param("param") ArticleVO param, Page page);



    @Select("<script>" +
            "select a.id,a.column_id,a.column_code,a.column_name, a.title, a.sub_title, a.sort_order, a.keyword, a.content, a.essential, a.is_top, a.is_major,a.publish_time," +
            "a.cover_img_url, a.link_url, a.type, a.author, a.source, a.state, a.is_show, a.is_focus, a.is_recommended,a.hit_nums,a.is_notice " +
            "from cms_article as a " +
            "<where>" +
            "   a.is_deleted = 0" +
            "<if test='param.state != null and param.state!=\"\"'>" +
            "   <choose>\n" +
            "      <when test='param.state == 5'>\n" +
            "           and a.state in  (2,3)" +
            "      </when>\n" +
            "      <otherwise>\n" +
            "           and a.state = #{param.state} " +
            "      </otherwise>\n" +
            "   </choose>  " +
            "</if> " +
            "<when test='param.columnCode != null and param.columnCode!=\"\" '>" +
            "   and a.column_code like concat(#{param.columnCode, jdbcType=VARCHAR},'%')" +
            "</when>" +
            "<when test='param.columnId != null and param.columnId !=\"\"'>" +
            "   and a.column_id=#{param.columnId}" +
            "</when>" +

            "<when test='param.isMajor != null and param.isMajor !=\"\"'>" +
            "   and is_major=#{param.isMajor}" +
            "</when>" +
            "<when test='param.isNotice != null and param.isNotice !=\"\"'>" +
            "   and is_notice=#{param.isNotice}" +
            "</when>" +
            "<if test='param.isMajor == null and param.isNotice ==null'>" +
            "   and is_major  is null  and  is_notice is null" +
            "</if>  " +
            "<if test='param.isFocus != null and param.isFocus !=\"\"'>" +
            "   and a.is_focus = #{param.isFocus}" +
            "</if>  " +
            "<if test='param.isTop != null and param.isTop !=\"\"'>" +
            "   and a.is_top = #{param.isTop}" +
            "</if>  " +
            "<when test='param.startTime != null'>" +
            "   and a.publish_time &gt;= #{param.startTime}" +
            "</when>"+
            "<when test='param.endTime != null'>" +
            "   and a.publish_time &lt;= #{param.endTime}" +
            "</when>"+
            "<when test='param.title != null and param.title!=\"\" '>" +
            "   and a.title like concat('%',#{param.title, jdbcType=VARCHAR},'%')" +
            "</when>" +
            "<when test='param.paramDateType != null and param.paramDateType ==1' >" +
            "   and TIMESTAMPDIFF(day,date_format(publish_time,'%Y-%m-%d'),date_format(NOW(),'%Y-%m-%d'))&lt;=#{param.paramDateNum}" +
            "</when>" +
            "<when test='param.paramDateType != null and param.paramDateType ==2' >" +
            "   and TIMESTAMPDIFF(WEEK,date_format(publish_time,'%Y-%m-%d'),date_format(NOW(),'%Y-%m-%d'))&lt;#{param.paramDateNum}" +
            "</when>" +
            "<when test='param.paramDateType != null and param.paramDateType == 3' >" +
            "   and TIMESTAMPDIFF(MONTH,date_format(publish_time,'%Y-%m-%d'),date_format(NOW(),'%Y-%m-%d'))&lt;#{param.paramDateNum}" +
            "</when>" +
            "<when test='param.paramDateType != null and param.paramDateType ==4' >" +
            "   and TIMESTAMPDIFF(YEAR,date_format(publish_time,'%Y-%m-%d'),date_format(NOW(),'%Y-%m-%d'))&lt;#{param.paramDateNum}" +
            "</when>" +
            "<if test='param.yearMonth != null and param.yearMonth !=\"\"'>" +
            "   and date_format(a.publish_time,'%Y-%m')= #{param.yearMonth}" +
            "</if>  " +
            "<if test='param.year != null and param.year !=\"\"'>" +
            "   and date_format(a.publish_time,'%Y')= #{param.year}" +
            "</if>  " +
            "<if test='param.month != null and param.month !=\"\"'>" +
            "   and date_format(a.publish_time,'%m')= #{param.month}" +
            "</if>  " +
            "</where>" +
            " ORDER BY a.publish_time desc, a.updated_time desc ,a.sort_order desc" +
            "</script>")
    Page<Article> listArticlesA(@Param("param") ArticleVO param, Page page);


    /**
     * 根据文章状态统计文章相关信息 0-草稿,1-待审核,2-审核通过,3-审核不通过
     * */
    @Select("<script>" +
            "select count(case when id is not null then 0 END) as allData, " +
            "       count(case when state = 0 then 0 end) as eidt, " +
            "       count(case when state = 1 then 0 end) as notApproved, " +
            "       count(case when state = 2 then 0 end) as approved, " +
            "       count(case when state = 3 then 0 end) as reject " +
            " from  cms_article " +
            "<where>" +
            "   is_deleted = 0 and is_major is null and  is_notice is null" +
            "<when test='startTime != null'>" +
            "   and cms_article.publish_time &gt;= #{startTime}" +
            "</when>"+
            "<when test='endTime != null'>" +
            "   and cms_article.publish_time &lt;= #{endTime}" +
            "</when>"+
            "<when test='monthTime != null and monthTime!=\"\"'>" +
            "   and time_format(cms_article.publish_time,'%m%')=#{monthTime}" +
            "</when>"+
            "</where>" +
            "</script>")
    @Results({@Result(column="id", property="id", id=true)})
    ArticleStatisticalResults statistics(@Param("monthTime") String monthTime,@Param("startTime")LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 按栏目统计文章信息 0-草稿,1-待审核,2-审核通过,3-审核不通过
     * */
    @Select("<script>" +
            "select column_id as columnId,column_name as name,\n" +
            "count(case when column_id is not null then 0 END) as allData \n" +
            "from cms_article\n" +
            "<where>" +
            "   is_deleted = 0 and is_major is null and  is_notice is null " +
            "   and column_code  like concat(column_code,'%') "+
            "   <when test='startTime != null'>" +
            "         and publish_time &gt;= #{startTime}" +
            "   </when>"+
            "   <when test='startTime != null'>" +
            "         and publish_time &lt;= #{endTime}" +
            "   </when>"+
            "   <when test='columnId != null'>" +
            "         and column_id= #{columnId}" +
            "   </when>"+
            "</where>" +
            "group by column_id,column_name order by allData desc LIMIT 8"+
            "</script>"
    )
    List<StatisticalResults> statisticByColumn(@Param("columnId")String columnId, @Param("startTime")LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);


    @Select("<script>" +
            "select count(id) as count" +
            "  from cms_article " +
            "<where>" +
            "   is_deleted = 0" +
            "<when test='columnId != null'>" +
            "   and column_id=#{columnId}" +
            "</when>" +
            "<if test='content != null and content !=\"\"'>" +
            "   and content= #{content}" +
            "</if>  " +
            "<if test='source != null and source !=\"\"'>" +
            "   and source= #{source}" +
            "</if>  " +
            "<when test='title!= null and title!=\"\"'>" +
            "   and title = #{title}" +
            "</when>"+
            "<when test='author != null and author!=\"\"'>" +
            "   and author= #{author}" +
            "</when>"+
            "</where>" +
            "</script>")
    int listCount(Map paramMap);


    /**
     *更新文章点击次数
     * */


    @Update("<script>" +
            "update cms_article  set hit_nums=IFNULL(hit_nums,0)+1  where id=#{id}" +
            "</script>")
    int  articlePoint(long id);


    /**
     * 用户发表文章统计
     */

    @Select("<script>" +
            "select created_name as name,  \n" +
            "count(case when column_id is not null then 0 END) as allData \n" +
            "from cms_article\n" +
            "<where>" +
            "   is_deleted = 0 and is_major is null and  is_notice is null " +
            "   <when test='createdBy != null'>" +
            "         and created_by=#{createdBy}" +
            "   </when>"+
            "   <when test='startTime != null'>" +
            "         and publish_time &gt;= #{startTime}" +
            "   </when>"+
            "   <when test='startTime != null'>" +
            "         and publish_time &lt;= #{endTime}" +
            "   </when>"+
            "</where>" +
            "group by created_name order by allData desc LIMIT 8"+
            "</script>"
    )
    List<StatisticalResults> statisticsByUser(Map parmaMap);



    /**
     * 统计自动采集节点发布信息
     * */
    @Select("<script>" +
            "select b.name as name,\n" +
            "count(case when a.column_id is not null then 0 END) as allData \n" +
            "from cms_article a,cms_collect_rule b\n" +
            "<where>" +
            "   a.is_deleted = 0 and a.is_major is null and  a.is_notice is null and a.state=2  " +
            "   and b.column_id=a.column_id"+
            "   <when test='startTime != null'>" +
            "         and a.publish_time &gt;= #{startTime}" +
            "   </when>"+
            "   <when test='startTime != null'>" +
            "         and a.publish_time &lt;= #{endTime}" +
            "   </when>"+
            "   <when test='name != null'>" +
            "         and b.name= #{pointName}" +
            "   </when>"+
            "</where>" +
            "group by b.name,a.column_name order by allData desc LIMIT 8"+
            "</script>"
    )
    List<StatisticalResults> statisticsByPointName(Map parmaMap);


    /**
     * 统计某栏目的发布信息
     * */
    @Select("<script>" +
            "select column_id,column_name,title,sub_title,keyword,essential,content,type,author,source,remarks,created_name\n" +
            " from cms_article\n" +
            "<where>" +
            "   is_deleted = 0 and is_major is null and  is_notice is null " +
            "   <when test='startTime != null'>" +
            "         and publish_time &gt;= #{startTime}" +
            "   </when>"+
            "   <when test='startTime != null'>" +
            "         and publish_time &lt;= #{endTime}" +
            "   </when>"+
            "   <when test='columnId != null'>" +
            "         and column_id= #{columnId}" +
            "   </when>"+
            "   <when test='state != null'>" +
            "         and state= #{state}" +
            "   </when>"+
            "</where>" +
            "</script>"
    )
    List<Article> statisticByColumnArticle(@Param("state") String state, @Param("columnId")String columnId, @Param("startTime")LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);



    /**
     * 统计某时间段栏目发布信息浏览总量
     * */
    @Select("<script>" +
            "select a.column_name as name,\n" +
            "sum(IFNULL(a.hit_nums,0)) as allData \n" +
            "from cms_article a\n" +
            "<where>" +
            "   a.is_deleted = 0 and a.is_major is null and  a.is_notice is null and a.state=3" +
            "   <when test='startTime != null'>" +
            "         and a.publish_time &gt;= #{startTime}" +
            "   </when>"+
            "   <when test='startTime != null'>" +
            "         and a.publish_time &lt;= #{endTime}" +
            "   </when>"+
            "   <when test='columnId != null'>" +
            "         and a.column_id= #{columnId}" +
            "   </when>"+
            "<when test='monthTime != null and monthTime!=\"\"'>" +
            "   and time_format(a.publish_time,'%m%')=#{monthTime}" +
            "</when>"+
            "</where>" +
            "group by a.column_name "+
            "</script>"
    )
    List<ArticleStatisticalResults> statisticsByHitNumsCount(Map parmaMap);

    /**
     * 总浏览量前8
     *
     * */
    @Select("<script>" +
            "select a.column_id  as columnId,a.column_name as name,\n" +
            "sum(IFNULL(a.hit_nums,0)) as allData \n" +
            "   from cms_article a\n" +
            "<where>" +
            "   a.is_deleted = 0 and a.is_major is null and  a.is_notice is null and a.state=2" +
            "   <when test='startTime != null'>" +
            "         and a.publish_time &gt;= #{startTime}" +
            "   </when>"+
            "   <when test='startTime != null'>" +
            "         and a.publish_time &lt;= #{endTime}" +
            "   </when>"+
            "   <when test='columnId != null'>" +
            "         and a.column_id= #{columnId}" +
            "   </when>"+
            "</where>" +
            "group by a.column_name,a.column_id order by allData desc LIMIT 8"+
            "</script>"
    )
    List<StatisticalResults> statisticsByHitNumsCountBefore(Map parmaMap);


   /**
    *网站总浏览量1-10天
    * */
    @Select("<script>" +
            "select sum(IFNULL(a.hit_nums,0)) as allData \n" +
            " from cms_article a\n" +
            "<where>" +
            "   a.is_deleted = 0 and a.is_major is null and  a.is_notice is null and a.state=2" +
            "<when test='dateNum != null and dateNum!=\"\"' >" +
            "   and TIMESTAMPDIFF(day,date_format(publish_time,'%Y-%m-%d'),date_format(NOW(),'%Y-%m-%d'))&lt;#{dateNum}" +
            "</when>" +
            "</where>" +
            "</script>"
    )
    StatisticalResults statisticsByHitNumsCountEveryDay(String dateNum);


    @Select("<script>" +
            "select a.id,a.column_id,a.column_code,a.column_name, a.title, a.sub_title, a.sort_order, a.keyword, a.content, a.essential, a.is_top, a.is_major,a.publish_time," +
            "a.cover_img_url, a.link_url, a.type, a.author, a.source, a.state, a.is_show, a.is_focus, a.is_recommended,a.hit_nums " +
            "from cms_article as a " +
            "<where>" +
            "   a.is_deleted = 0  and a.is_major  is null  and  a.is_notice is null" +
            "<when test='articleVO.columnCode != null and articleVO.columnCode!=\"\" '>" +
            "   and a.column_code like concat(#{articleVO.columnCode},'%')" +
            "</when>" +
            "<if test='articleVO.isFocus != null and articleVO.isFocus !=\"\"'>" +
            "   and a.is_focus = #{articleVO.isFocus}" +
            "</if>  " +
            "<if test='articleVO.state != null and articleVO.state!=\"\"'>" +
            "           and a.state = #{articleVO.state} " +
            "</if> " +
            "</where>" +
            "  ORDER BY a.sort_order, a.publish_time desc, a.updated_time desc" +
            "</script>")
    Page<Article> listArticlesInfor(ArticleVO articleVO, Page page);




}

