package com.eicas.ecms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eicas.ecms.entity.MenuRole;

import java.util.List;

/**
 * <p>
 * 菜单角色权限表 服务类
 * </p>
 *
 * @author jsq
 * @since 2021-11-12
 */
public interface IMenuRoleService extends IService<MenuRole> {

    /**
     * 菜单角色权限表分页列表
     * @param param 根据需要进行传值
     * @return
     */
    public Page<MenuRole> page(MenuRole entity, Page<MenuRole> page);


    /**
     * 菜单角色权限表详情
     * @param id
     * @return
     */
    MenuRole info(Long id);

    /**
    * 菜单角色权限表新增
    * @param param 根据需要进行传值
    * @return
    */
    void add(MenuRole param);

    /**
    * 菜单角色权限表修改
    * @param param 根据需要进行传值
    * @return
    */
    void modify(MenuRole param);

    /**
    * 菜单角色权限表删除(单个条目)
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
