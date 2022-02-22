package com.eicas.ecms.mapper;

import com.eicas.ecms.entity.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色权限表 Mapper 接口
 * </p>
 *
 * @author jsq
 * @since 2021-11-29
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    List<RolePermission> queryByRoleId(String roleId);

}
