package com.eicas.ecms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eicas.ecms.entity.ColumnType;

import java.util.List;

/**
 * <p>
 * 栏目类型表 服务类
 * </p>
 *
 * @author jsq
 * @since 2021-11-29
 */
public interface IColumnTypeService extends IService<ColumnType> {

    /**
     * 栏目类型表分页列表
     * @return Page<ColumnType>
     */
    Page<ColumnType> page(ColumnType entity, Page<ColumnType> page);


    /**
     * 栏目类型表详情
     * @param id id
     * @return ColumnType
     */
    ColumnType info(Long id);

    /**
     * 栏目类型表新增
     * @param param 根据需要进行传值
     */
    void add(ColumnType param);

    /**
     * 栏目类型表修改
     * @param param 根据需要进行传值
     */
    void modify(ColumnType param);

    /**
     * 栏目类型表删除(单个条目)
     * @param id id
     */
    void remove(Long id);

    /**
     * 删除(多个条目)
     * @param ids
     */
    void removes(List<Long> ids);
}
