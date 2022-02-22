package com.eicas.ecms.service.impl;

import com.eicas.ecms.entity.RolePermission;
import com.eicas.ecms.mapper.RolePermissionMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.ecms.service.IRolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 角色权限表 服务实现类
 * </p>
 *
 * @author jsq
 * @since 2021-11-29
*/
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

    /**
     * 角色权限表分页列表
     * @param param 根据需要进行传值
     * @return
     */
    @Override
    public Page<RolePermission> page(RolePermission param, Page<RolePermission> page) {

        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            // 
                    .eq(StringUtils.hasText(param.getId()), RolePermission::getId, param.getId())
                    // 角色id
                    .eq(StringUtils.hasText(param.getRoleId()), RolePermission::getRoleId, param.getRoleId())
                    // 权限id
                    .eq(StringUtils.hasText(param.getPermissionId()), RolePermission::getPermissionId, param.getPermissionId())
        ;

        return page(page, queryWrapper);
    }

    @Override
    public RolePermission info(Long id) {
    return null;
    }

    @Override
    public void add(RolePermission param) {

    }

    @Override
    public void modify(RolePermission param) {

    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void removes(List<Long> ids) {

    }
}
