package com.eicas.ecms.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.ecms.entity.User;
import com.eicas.ecms.dto.UserDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author jsq
 * @since 2021-10-28
 */
public interface UserMapper extends BaseMapper<User> {
    Page<UserDto> page(UserDto entity, Page<UserDto> page);
}
