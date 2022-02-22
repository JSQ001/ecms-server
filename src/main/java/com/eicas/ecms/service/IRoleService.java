package com.eicas.ecms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eicas.ecms.dto.RoleAssignPermissionParams;
import com.eicas.ecms.entity.Role;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author jsq
 * @since 2021-11-29
 */
public interface IRoleService extends IService<Role> {

    /**
     * 角色表分页列表
     * @param entity 根据需要进行传值
     * @return Page
     */
     Page<Role> page(Role entity, Page<Role> page);


    /**
     * 角色表详情
     * @param id id
     * @return Role
     */
    Role info(Long id);

    /**
     * 角色表新增
     * @param param 根据需要进行传值
     */
    void add(Role param);

    /**
     * 角色表修改
     * @param param 根据需要进行传值
     */
    void modify(Role param);

    /**
     * 角色表删除(单个条目)
     * @param id id
     */
    void remove(Long id);

    /**
     * 删除(多个条目)
     * @param ids ids
     */
    void removes(List<Long> ids);
    /**
     * 角色表分配权限
     * @param params 根据需要进行传值
     * @return boolean
     */
    boolean roleAssignAuthority(RoleAssignPermissionParams params);


}
