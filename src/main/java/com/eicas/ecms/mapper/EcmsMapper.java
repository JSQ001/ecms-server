package com.eicas.ecms.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.ecms.dto.ArticleRuleDto;
import com.eicas.ecms.entity.Ecms;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author jsq
 * @since 2021-10-28
 */
@Repository
public interface EcmsMapper extends BaseMapper<Ecms> {
    Page<Ecms> page(Ecms entity, Page<Ecms> page);

    @Update({"update info_ecms set status = #{status} where id = #{id}"})
    Boolean statusUpdate(Ecms ecms);

    @Select({"<script>",
            "select count(*) from info_ecms where 1=1",
            "<if test='author != null and author!=\"\"'>and author = #{author}</if>",
            "<if test='title != null and title!=\"\"'>and title = #{title}</if>",
            "<if test='content != null and content!=\"\"'>and content = #{content}</if>",
            "</script>"})
    Integer selectNum(Ecms ecms);

    @Update("update  info_ecms  set column_status=1  where id =#{id}")
    void updataStatus(@Param("id") String id,@Param("status") Integer status);

    @Update("update  info_ecms  set column_status=1 ,tips=#{ecms.tips} where id =#{ecms.id}")
    void updateStatusAndTips(@Param("ecms") Ecms ecms);

    @Update("update  info_ecms  set column_status=2  where id =#{id}")
    Boolean updataAiledStatus(String id);
    @Update("update  info_ecms  set column_status=2 ,tips=#{ecms.tips} where id =#{ecms.id}")
    Boolean updateAiledStatusAndTips(@Param("ecms") Ecms ecms);

    @Select("select e.id as id ,e.column_id as column_id,e.content as content , e.title as title," +
            "e.sub_title as sub_title ,e.attachment as attachment ,e.link_url as link_url,e.keyword as keyword," +
            "e.remarks as remarks,e.img as img,e.source as source,e.author as author, e.type as type," +
            "e.column_status as column_status,e.sort as sort, e.hit_times as hit_times,e.created_time as created_time," +
            "e.updated_time as updated_time,e.created_by as created_by,e.focus_img as focus_img ,e.tips as tips " +
            " from info_ecms_rule as r " +
            "INNER  join info_ecms as e " +
            "ON r.ecmas_id=e.id " +
            "WHERE r.rule_id=#{articleRuleDto.ruleId} " +
            "AND e.column_status=#{articleRuleDto.status} " +
            "AND e.is_deleted=0 " +
            "LIMIT  #{start},#{end};")
//   ${(pageNo-1)*pageSize},${pageSize}
    List<Ecms> selectAuditStatus(@Param("articleRuleDto")ArticleRuleDto articleRuleDto, @Param("end") Integer end ,@Param("start")Integer start);

    @Select("select count(*) " +
            "from info_ecms_rule as r " +
            "INNER  join info_ecms as e " +
            "ON r.ecmas_id=e.id " +
            "WHERE r.rule_id=#{articleRuleDto.ruleId} " +
            "AND e.column_status=#{articleRuleDto.status} " +
            "AND e.is_deleted=0 ")
    Long selectCountMyself(@Param("articleRuleDto") ArticleRuleDto articleRuleDto);
}
