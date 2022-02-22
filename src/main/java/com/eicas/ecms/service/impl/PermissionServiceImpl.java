package com.eicas.ecms.service.impl;

import com.eicas.ecms.entity.Permission;
import com.eicas.ecms.mapper.PermissionMapper;
import com.eicas.ecms.service.IPermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author jsq
 * @since 2021-11-29
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    /**
     * 权限表分页列表
     * @param param 根据需要进行传值
     * @return
     */
    @Override
    public Page<Permission> page(Permission param, Page<Permission> page) {

        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            // 
                    .eq(StringUtils.hasText(param.getId()), Permission::getId, param.getId())
                    // 权限代码
                    .eq(StringUtils.hasText(param.getPermissionCode()), Permission::getPermissionCode, param.getPermissionCode())
                    // 权限名称
                    .eq(StringUtils.hasText(param.getPermissionName()), Permission::getPermissionName, param.getPermissionName())
                    // 创建时间
                    .eq(param.getCreatedTime() != null, Permission::getCreatedTime, param.getCreatedTime())
                    // 更新时间
                    .eq(param.getUpdatedTime() != null, Permission::getUpdatedTime, param.getUpdatedTime())
                    // 创建人
                    .eq(StringUtils.hasText(param.getCreatedBy()), Permission::getCreatedBy, param.getCreatedBy())
                    // 更新人
                    .eq(StringUtils.hasText(param.getUpdatedBy()), Permission::getUpdatedBy, param.getUpdatedBy())
                    // 逻辑删除
                    .eq(param.getDeleted() != null, Permission::getDeleted, param.getDeleted())
        ;

        return page(page, queryWrapper);
    }

    @Override
    public Permission info(Long id) {
    return null;
    }

    @Override
    public void add(Permission param) {

    }

    @Override
    public void modify(Permission param) {

    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void removes(List<Long> ids) {

    }
}
