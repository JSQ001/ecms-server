package com.eicas.cms.service.impl;

import com.eicas.cms.pojo.entity.Column;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.mapper.ColumnMapper;
import com.eicas.cms.pojo.vo.ColumnVO;
import com.eicas.cms.service.IColumnService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 栏目表 服务实现类
 * </p>
 *
 * @author jsq
 * @since 2022-03-04
*/
@Service
@Transactional
public class ColumnServiceImpl extends ServiceImpl<ColumnMapper, Column> implements IColumnService {

    @Resource
    private ColumnMapper columnMapper;

    @Override
    public Page<Column> listColumns(ColumnVO columnVo) {

        Page<Column> result = columnMapper.getTreePage(columnVo, columnVo.pageFactory() );
        if(StringUtils.hasText(columnVo.getName())){
            result.getRecords().forEach(i->{
                filter(i,columnVo);
            });
        }
        return result;
    }

    /**
     * 递归去重
     * */
    Long filter(Column column,ColumnVO param){
        if(column.getChildren()!=null){
            List<Long> testList = new ArrayList<>();
            column.getChildren().forEach(i->{
                //不满足条件，删除
                Long id = filter(i,param);
                if(id!=null){
                    testList.add(id);
                }
            });
            List<Column> newList = column.getChildren().stream().filter(i->testList.contains(i.getId())).collect(Collectors.toList());
            column.setChildren(newList);
            return column.getName().contains(param.getName()) || newList.size() >0  ? column.getId() : null;
        }else{
            //返回满足条件的id
            return column.getName().contains(param.getName()) ? column.getId() : null;
        }
    }


    @Override
    public List<Column> getColumnTree(Long id) {
        return columnMapper.selectTreeByParentId(id);
    }

    @Override
    public boolean logicalDeleteById(Long id) {
        return removeById(id);
    }

    @Override
    public boolean createOrUpdate(Column entity) {
        return saveOrUpdate(entity);
    }


}
