package com.eicas.ecms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.ecms.entity.CollectionRule;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 采集规则表 Mapper 接口
 * </p>
 *
 * @author jsq
 * @since 2021-11-09
 */
@Repository
public interface CollectionRuleMapper extends BaseMapper<CollectionRule> {
    Page<CollectionRule> page(CollectionRule entity, Page<CollectionRule> page);

    @Select({"<script>",
            "select columnId from tb_collection_rule where 1=1",
            "<if test='startDate != null and startDate!=\"\"'>and startDate &lt;= #{date}</if>",
            "<if test='endDate != null and endDate!=\"\"'>and endDate &gt;= #{date}</if>",
            "</script>"})
    List<String> selectColumnId(String date);


}
