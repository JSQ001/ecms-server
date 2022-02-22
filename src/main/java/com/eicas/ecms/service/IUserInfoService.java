package com.eicas.ecms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eicas.ecms.entity.UserInfo;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author jsq
 * @since 2021-10-28
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 用户信息表分页列表
     * @param param 根据需要进行传值
     * @return
     */
    public Page<UserInfo> page(UserInfo entity, Page<UserInfo> page);


    /**
     * 用户信息表详情
     * @param id
     * @return
     */
    UserInfo info(Long id);

    /**
    * 用户信息表新增
    * @param param 根据需要进行传值
    * @return
    */
    void add(UserInfo param);

    /**
    * 用户信息表修改
    * @param param 根据需要进行传值
    * @return
    */
    void modify(UserInfo param);

    /**
    * 用户信息表删除(单个条目)
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
