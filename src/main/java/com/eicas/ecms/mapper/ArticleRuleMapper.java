package com.eicas.ecms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eicas.ecms.entity.ArticleRule;
import com.eicas.ecms.dto.ArticleRuleDto;
import org.apache.ibatis.annotations.Select;
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
public interface ArticleRuleMapper extends BaseMapper<ArticleRule> {

    @Select({"<script>",
            "select * from info_ecms_rule where 1=1",
            "<if test='columnId != null and columnId!=\"\"'>and columnId = #{columnId}</if>",
            "<if test='ruleId != null and ruleId!=\"\"'>and rule_id = #{ruleId}</if>",
            "<if test='createdTime != null and createdTime!=\"\"'>and created_time = #{createdTime}</if>",
            "</script>"})
    List<ArticleRule> selectColumnId(ArticleRuleDto articleRule);

    @Select({"<script>",
            "select count(*) from info_ecms_rule where 1=1",
            "<if test='columnId != null and columnId!=\"\"'>and column_id = #{columnId}</if>",
            "<if test='ruleId != null and ruleId!=\"\"'>and rule_id = #{ruleId}</if>",
            "<if test='status != null and status!=\"\"'>and ecms_status = #{status}</if>",
            "<if test='start != null and start!=\"\"'>and created_time  &gt;= #{start}</if>",
            "<if test='end != null and end!=\"\"'>and created_time  &lt;= #{end}</if>",
            "</script>"})
    Integer selectNum(ArticleRuleDto articleRule);

    @Select("select * from info_ecms_rule where ecms_id = #{id}")
    ArticleRule selectByArticle(String id);

}
