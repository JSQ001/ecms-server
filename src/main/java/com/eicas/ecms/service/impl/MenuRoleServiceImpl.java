package com.eicas.ecms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.ecms.entity.MenuRole;
import com.eicas.ecms.mapper.MenuRoleMapper;
import com.eicas.ecms.service.IMenuRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 菜单角色权限表 服务实现类
 * </p>
 *
 * @author jsq
 * @since 2021-11-12
*/
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    /**
     * 菜单角色权限表分页列表
     * @param param 根据需要进行传值
     * @return page
     */
    @Override
    public Page<MenuRole> page(MenuRole param, Page<MenuRole> page) {

        QueryWrapper<MenuRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            // 
                    .eq(StringUtils.hasText(param.getId()), MenuRole::getId, param.getId())
                    // 角色id
                    .eq(StringUtils.hasText(param.getRoleId()), MenuRole::getRoleId, param.getRoleId())
                    // 菜单id
                    .eq(StringUtils.hasText(param.getMenuId()), MenuRole::getMenuId, param.getMenuId())
        ;

        return page(page, queryWrapper);
    }

    @Override
    public MenuRole info(Long id) {
    return null;
    }

    @Override
    public void add(MenuRole param) {

    }

    @Override
    public void modify(MenuRole param) {

    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void removes(List<Long> ids) {

    }
}
