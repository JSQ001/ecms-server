package com.eicas.ecms.mapper;

import com.eicas.ecms.dto.ArticleRuleDto;
import com.eicas.ecms.entity.Column;
import com.eicas.ecms.entity.Menu;
import com.eicas.ecms.pojo.ColumnPojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 栏目表 Mapper 接口
 * </p>
 *
 * @author jsq
 * @since 2021-11-29
 */
public interface ColumnMapper extends BaseMapper<Column> {
    Page<Column> page(ColumnPojo entity, Page<Column> page);
    List<Menu> getChildren(@Param("id")String id);
    Page<Column> getList(Column entity, Page<Column> page);

    @Select("select c.column_name as columnName from info_ecms_rule as r inner join info_column as c on r.column_id =c.id where r.rule_id=#{articleRuleDto.ruleId} limit 1")
    String selectColumnName(@Param("articleRuleDto") ArticleRuleDto articleRuleDto);
}
