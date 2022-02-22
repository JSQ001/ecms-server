package com.eicas.ecms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.ecms.entity.User;
import com.eicas.ecms.dto.UserDto;
import com.eicas.ecms.entity.UserInfo;
import com.eicas.ecms.mapper.UserMapper;
import com.eicas.ecms.service.IUserInfoService;
import com.eicas.ecms.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author jsq
 * @since 2021-10-28
*/
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private IUserInfoService iUserInfoService;
    @Resource
    private UserMapper userMapper;
    /**
     * 用户表分页列表
     * @param param 根据需要进行传值
     * @return
     */
    @Override
    public Page<UserDto> page(UserDto param, Page<UserDto> page) {
        return userMapper.page(param,page);
    }

    @Override
    public boolean mySaveOrUpdate(UserDto entity) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(entity.getUserInfoId());
        userInfo.setPhone(entity.getPhone());
        userInfo.setRoleId(entity.getRoleId());

        //保存用户信息
        boolean success = iUserInfoService.saveOrUpdate(userInfo);

        //保存账号信息
        User user = new User();
        user.setId(entity.getId());
        user.setUserName(entity.getUsername());
        user.setUserInfoId(userInfo.getId());
        user.setPwd(entity.getPassword());
        saveOrUpdate(user);

        return success;
    }

    @Override
    public User info(Long id) {
    return null;
    }

    @Override
    public void add(User param) {

    }

    @Override
    public void modify(User param) {

    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void removes(List<Long> ids) {

    }
}
