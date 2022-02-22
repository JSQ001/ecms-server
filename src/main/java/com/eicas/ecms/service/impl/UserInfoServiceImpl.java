package com.eicas.ecms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.ecms.entity.UserInfo;
import com.eicas.ecms.mapper.UserInfoMapper;
import com.eicas.ecms.service.IUserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author jsq
 * @since 2021-11-29
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    /**
     * 用户信息表分页列表
     * @param param 根据需要进行传值
     * @return
     */
    @Override
    public Page<UserInfo> page(UserInfo param, Page<UserInfo> page) {

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                //
                .eq(StringUtils.hasText(param.getId()), UserInfo::getId, param.getId())
                // 联系方式
                .eq(StringUtils.hasText(param.getPhone()), UserInfo::getPhone, param.getPhone())
                // 角色id
                .eq(StringUtils.hasText(param.getRoleId()), UserInfo::getRoleId, param.getRoleId())
                // 创建时间
                .eq(param.getCreatedTime() != null, UserInfo::getCreatedTime, param.getCreatedTime())
                // 更新时间
                .eq(param.getUpdatedTime() != null, UserInfo::getUpdatedTime, param.getUpdatedTime())
                // 创建人
                .eq(StringUtils.hasText(param.getCreatedBy()), UserInfo::getCreatedBy, param.getCreatedBy())
                // 更新人
                .eq(StringUtils.hasText(param.getUpdatedBy()), UserInfo::getUpdatedBy, param.getUpdatedBy())
                // 逻辑删除
                .eq(param.getIsDeleted() != null, UserInfo::getIsDeleted, param.getIsDeleted())
        ;

        return page(page, queryWrapper);
    }

    @Override
    public UserInfo info(Long id) {
        return null;
    }

    @Override
    public void add(UserInfo param) {

    }

    @Override
    public void modify(UserInfo param) {

    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void removes(List<Long> ids) {

    }
}
