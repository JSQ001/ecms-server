package com.eicas.ecms.service;

import com.eicas.ecms.entity.RolePermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色权限表 服务类
 * </p>
 *
 * @author jsq
 * @since 2021-11-29
 */
public interface IRolePermissionService extends IService<RolePermission> {

    /**
     * 角色权限表分页列表
     * @param param 根据需要进行传值
     * @return
     */
    public Page<RolePermission> page(RolePermission entity, Page<RolePermission> page);


    /**
     * 角色权限表详情
     * @param id
     * @return
     */
    RolePermission info(Long id);

    /**
    * 角色权限表新增
    * @param param 根据需要进行传值
    * @return
    */
    void add(RolePermission param);

    /**
    * 角色权限表修改
    * @param param 根据需要进行传值
    * @return
    */
    void modify(RolePermission param);

    /**
    * 角色权限表删除(单个条目)
    * @param id
    * @return
    */
    void remove(Long id);

    /**
    * 删除(多个条目)
    * @param ids
    * @return
    */
    void removes(List<Long> ids);
}
