package com.eicas.ecms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eicas.ecms.entity.User;
import com.eicas.ecms.dto.UserDto;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author jsq
 * @since 2021-10-28
 */
public interface IUserService extends IService<User> {

    /**
     * 用户表分页列表
     * @param param 根据需要进行传值
     * @return
     */
    public Page<UserDto> page(UserDto entity, Page<UserDto> page);

    public boolean mySaveOrUpdate(UserDto entity);


    /**
     * 用户表详情
     * @param id
     * @return
     */
    User info(Long id);

    /**
    * 用户表新增
    * @param param 根据需要进行传值
    * @return
    */
    void add(User param);

    /**
    * 用户表修改
    * @param param 根据需要进行传值
    * @return
    */
    void modify(User param);

    /**
    * 用户表删除(单个条目)
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
