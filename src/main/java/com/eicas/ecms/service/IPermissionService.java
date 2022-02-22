package com.eicas.ecms.service;

import com.eicas.ecms.entity.Permission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author jsq
 * @since 2021-11-29
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 权限表分页列表
     * @param entity 根据需要进行传值
     * @return Page
     */
    Page<Permission> page(Permission entity, Page<Permission> page);


    /**
     * 权限表详情
     * @param id id
     * @return Permission
     */
    Permission info(Long id);

    /**
    * 权限表新增
    * @param param 根据需要进行传值
    */
    void add(Permission param);

    /**
    * 权限表修改
    * @param param 根据需要进行传值
    */
    void modify(Permission param);

    /**
    * 权限表删除(单个条目)
    * @param id id
    */
    void remove(Long id);

    /**
    * 删除(多个条目)
    * @param ids ids
    */
    void removes(List<Long> ids);
}
