package com.eicas.ecms.service.impl;

import com.eicas.ecms.entity.Column;
import com.eicas.ecms.mapper.ColumnMapper;
import com.eicas.ecms.pojo.ColumnPojo;
import com.eicas.ecms.service.IColumnService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 栏目表 服务实现类
 * </p>
 *
 * @author jsq
 * @since 2021-11-29
*/
@Service
public class ColumnServiceImpl extends ServiceImpl<ColumnMapper, Column> implements IColumnService {

    @Resource
    private ColumnMapper columnMapper;
    /**
     * 栏目表分页列表
     * @param param 根据需要进行传值
     * @return
     */
    @Override
    public Page<Column> page(ColumnPojo param, Page<Column> page) {
        return columnMapper.getList(param, page);
    }

    @Override
    public Column info(Long id) {
    return null;
    }

    @Override
    public void add(Column param) {

    }

    @Override
    public void modify(Column param) {

    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void removes(List<Long> ids) {

    }
}
