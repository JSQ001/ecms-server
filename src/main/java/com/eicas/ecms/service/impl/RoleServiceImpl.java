package com.eicas.ecms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.ecms.dto.RoleAssignPermissionParams;
import com.eicas.ecms.entity.Role;
import com.eicas.ecms.mapper.RoleMapper;
import com.eicas.ecms.service.IRolePermissionService;
import com.eicas.ecms.service.IRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author jsq
 * @since 2021-11-29
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private IRolePermissionService iRolePermissionService;

    @Resource
    private RoleMapper roleMapper;
    /**
     * 角色表分页列表
     * @param param 根据需要进行传值
     * @return Page
     */
    @Override
    public Page<Role> page(Role param, Page<Role> page) {
        return roleMapper.page(param,page);
    }

    @Override
    public Role info(Long id) {
        return null;
    }

    @Override
    public void add(Role param) {

    }

    @Override
    public void modify(Role param) {

    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void removes(List<Long> ids) {

    }

    @Override
    public boolean roleAssignAuthority(RoleAssignPermissionParams params) {
        // 删除原来权限
        iRolePermissionService.removeByIds(params.getDelList());
        // 插入新增的
        return iRolePermissionService.saveBatch(params.getAddList());
    }
}
