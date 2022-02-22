package com.eicas.ecms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.ecms.entity.Role;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author jsq
 * @since 2021-11-11
 */
public interface RoleMapper extends BaseMapper<Role> {
    Page<Role> page(Role entity, Page<Role> page);
}
