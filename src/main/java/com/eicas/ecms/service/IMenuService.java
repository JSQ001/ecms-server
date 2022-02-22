package com.eicas.ecms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eicas.ecms.dto.MenuAssignRoleParams;
import com.eicas.ecms.entity.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author jsq
 * @since 2021-10-28
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 菜单表分页列表
     * @param param 根据需要进行传值
     * @return
     */
    public Page<Menu> page(Menu entity, Page<Menu> page);

    /**
     * 菜单分配角色
     * @param param 根据需要进行传值
     * @return
     */
    public boolean menuAssignRole(MenuAssignRoleParams params);


    /**
     * 菜单树
     * @param param 根据需要进行传值
     * @return
     */
    public List<Menu> getTree();


    /**
     * 菜单表详情
     * @param id
     * @return
     */
    Menu info(Long id);

    /**
    * 菜单表新增
    * @param param 根据需要进行传值
    * @return
    */
    void add(Menu param);

    /**
    * 菜单表修改
    * @param param 根据需要进行传值
    * @return
    */
    void modify(Menu param);

    /**
    * 菜单表删除(单个条目)
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
