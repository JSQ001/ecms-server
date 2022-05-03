package com.eicas.security.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eicas.security.entity.AuthPermit;
import com.eicas.security.entity.AuthRole;

import java.util.List;

/**
 * 角色信息服务类
 *
 * @author osnudt
 * @since 2022-04-23
 */
public interface IAuthRoleService extends IService<AuthRole> {

    /**
     * 根据角色名称模糊查询角色信息
     *
     * @param keyword 角色名称
     * @param current 当前分页
     * @param size    分页大小
     * @return 角色对象分页数据
     */
    Page<AuthRole> listRole(String keyword, Integer current, Integer size);

    /**
     * 根据角色ID查询角色所对应的权限
     *
     * @param roleId 角色ID
     * @return 权限对象列表
     */
    List<AuthPermit> listPermit(Long roleId);

    /**
     * 为指定ID的角色分配关联的权限
     *
     * @param roleId    角色ID
     * @param permitIds 权限点ID
     * @return 分配是否成功
     */
    boolean allocPermit(Long roleId, List<Long> permitIds);

}
