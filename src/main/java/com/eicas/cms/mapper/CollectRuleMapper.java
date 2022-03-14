package com.eicas.cms.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.entity.Article;
import com.eicas.cms.pojo.entity.CollectRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eicas.cms.pojo.vo.CollectRuleVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 采集规则表 Mapper 接口
 * </p>
 *
 * @author jsq
 * @since 2022-03-06
 */
public interface CollectRuleMapper extends BaseMapper<CollectRule> {
    /**
     * 分页条件查询article
     * */
    @Select("<script>" +
            "select r.id,column_id, r.name, c.name as columnName, is_auto " +
            "from cms_collect_rule as r, cms_column as c" +
            "<where>" +
            "   column_id = c.id and r.is_deleted = 0 " +
            "<when test='entity.name != null and entity.name!=\"\" '>" +
            "   and r.name like concat('%',#{entity.name, jdbcType=VARCHAR},'%')" +
            "</when>" +
            "</where>" +
            " ORDER BY r.updated_time" +
            "</script>")
    Page<CollectRule> listArticles(@Param("entity") CollectRuleVO param, Page<CollectRule> page);
}
