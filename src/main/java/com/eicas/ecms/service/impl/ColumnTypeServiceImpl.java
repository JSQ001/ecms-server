package com.eicas.ecms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.ecms.entity.ColumnType;
import com.eicas.ecms.mapper.ColumnTypeMapper;
import com.eicas.ecms.service.IColumnTypeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 栏目类型表 服务实现类
 * </p>
 *
 * @author jsq
 * @since 2021-11-29
 */
@Service
public class ColumnTypeServiceImpl extends ServiceImpl<ColumnTypeMapper, ColumnType> implements IColumnTypeService {

    /**
     * 栏目类型表分页列表
     * @param param 根据需要进行传值
     * @return
     */
    @Override
    public Page<ColumnType> page(ColumnType param, Page<ColumnType> page) {
        QueryWrapper<ColumnType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                //
                .eq(StringUtils.hasText(param.getId()), ColumnType::getId, param.getId())
                // 栏目类型名称
                .eq(StringUtils.hasText(param.getColumnTypeName()), ColumnType::getColumnTypeName, param.getColumnTypeName())
                // 创建时间
                .eq(param.getCreatedTime() != null, ColumnType::getCreatedTime, param.getCreatedTime())
                // 更新时间
                .eq(param.getUpdatedTime() != null, ColumnType::getUpdatedTime, param.getUpdatedTime())
                // 创建人
                .eq(StringUtils.hasText(param.getCreatedBy()), ColumnType::getCreatedBy, param.getCreatedBy())
                // 更新人
                .eq(StringUtils.hasText(param.getUpdatedBy()), ColumnType::getUpdatedBy, param.getUpdatedBy())
                // 逻辑删除
                .eq(param.getIsDeleted() != null, ColumnType::getIsDeleted, param.getIsDeleted())
        ;

        return page(page, queryWrapper);
    }

    @Override
    public ColumnType info(Long id) {
        return null;
    }

    @Override
    public void add(ColumnType param) {

    }

    @Override
    public void modify(ColumnType param) {

    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void removes(List<Long> ids) {

    }
}
