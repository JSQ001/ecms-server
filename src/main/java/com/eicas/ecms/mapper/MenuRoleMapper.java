package com.eicas.ecms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eicas.ecms.entity.MenuRole;

import java.util.List;

/**
 * <p>
 * 菜单角色权限表 Mapper 接口
 * </p>
 *
 * @author jsq
 * @since 2021-11-12
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {
    List<MenuRole> queryByMenuId(String menuId);
}
