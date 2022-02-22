package com.eicas.ecms.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eicas.ecms.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author jsq
 * @since 2021-10-28
 */
public interface MenuMapper extends BaseMapper<Menu> {
    // 根据id查询节点树
    List<Menu> getTree(@Param("id")String id);
    Page<Menu> getList(Menu entity, Page<Menu> page);
}
