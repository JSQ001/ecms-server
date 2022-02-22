package com.eicas.ecms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.ecms.dto.MenuAssignRoleParams;
import com.eicas.ecms.entity.Menu;
import com.eicas.ecms.mapper.MenuMapper;
import com.eicas.ecms.service.IMenuRoleService;
import com.eicas.ecms.service.IMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author jsq
 * @since 2021-10-28
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private IMenuRoleService iMenuRoleService;

    /**
     * 菜单表分页列表
     * @param param 根据需要进行传值
     * @return page
     */
    @Override
    public Page<Menu> page(Menu param, Page<Menu> page) {
        return menuMapper.getList(param, page);
    }

    @Override
    public boolean menuAssignRole(MenuAssignRoleParams params) {
        // 删除原来权限
        iMenuRoleService.removeByIds(params.getDelList());
        // 插入新增的
        return iMenuRoleService.saveBatch(params.getAddList());
    }

    @Override
    public List<Menu> getTree() {
        return menuMapper.getTree(null);
    }

    @Override
    public Menu info(Long id) {
    return null;
    }

    @Override
    public void add(Menu param) {

    }

    @Override
    public void modify(Menu param) {

    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void removes(List<Long> ids) {

    }
}
