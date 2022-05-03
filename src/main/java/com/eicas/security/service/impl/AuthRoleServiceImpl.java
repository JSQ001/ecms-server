package com.eicas.security.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.security.entity.AuthPermit;
import com.eicas.security.entity.AuthRole;
import com.eicas.security.mapper.AuthRoleMapper;
import com.eicas.security.service.IAuthRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色信息服务实现类
 *
 * @author osnudt
 * @since 2022-04-23
 */
@Service
public class AuthRoleServiceImpl extends ServiceImpl<AuthRoleMapper, AuthRole> implements IAuthRoleService {

    @Override
    public Page<AuthRole> listRole(String keyword, Integer current, Integer size) {
        // TODO
        return null;
    }

    @Override
    public List<AuthPermit> listPermit(Long roleId) {
        // TODO
        return null;
    }

    @Override
    public boolean allocPermit(Long roleId, List<Long> permitIds) {
        // TODO
        return false;
    }
}
