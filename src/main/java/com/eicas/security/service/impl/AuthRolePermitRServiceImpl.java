package com.eicas.security.service.impl;

import com.eicas.security.entity.AuthRolePermitR;
import com.eicas.security.mapper.AuthRolePermitRMapper;
import com.eicas.security.service.IAuthRolePermitRService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和权限关联表 服务实现类
 * </p>
 *
 * @author osnudt
 * @since 2022-04-27
 */
@Service
public class AuthRolePermitRServiceImpl extends ServiceImpl<AuthRolePermitRMapper, AuthRolePermitR> implements IAuthRolePermitRService {

}
