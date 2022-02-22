package com.eicas.ecms.service;

import com.eicas.ecms.entity.Column;
import com.eicas.ecms.pojo.ColumnPojo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 栏目表 服务类
 * </p>
 *
 * @author jsq
 * @since 2021-11-29
 */
public interface IColumnService extends IService<Column> {

    /**
     * 栏目表分页列表
     * @param param 根据需要进行传值
     * @return
     */
    public Page<Column> page(ColumnPojo param, Page<Column> page);


    /**
     * 栏目表详情
     * @param id
     * @return
     */
    Column info(Long id);

    /**
    * 栏目表新增
    * @param param 根据需要进行传值
    * @return
    */
    void add(Column param);

    /**
    * 栏目表修改
    * @param param 根据需要进行传值
    * @return
    */
    void modify(Column param);

    /**
    * 栏目表删除(单个条目)
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
