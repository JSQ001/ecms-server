package com.eicas.cms.service.impl;

import com.eicas.cms.exception.BusinessException;
import com.eicas.cms.mapper.ArticleMapper;
import com.eicas.cms.pojo.entity.Column;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.mapper.ColumnMapper;
import com.eicas.cms.pojo.enumeration.ResultCode;
import com.eicas.cms.pojo.vo.ColumnVO;
import com.eicas.cms.service.IColumnService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.transaction.annotation.Transactional;
//import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
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
    @Resource
    private ArticleMapper articleMapper;

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

        Column column=this.getById(id);
        Map<String,Object>  columnMap= new HashMap<>();
        int len=column.getColumnCode().length()+2;
        columnMap.put("columnCode",column.getColumnCode());

        columnMap.put("sortOrder",len);
        int count=columnMapper.selectColumnCount(columnMap);
        if (count>0){
            return false;

        }

        if (columnMapper.selectArticleByColumnId(id).size()>0)    throw new BusinessException(ResultCode.ARTICLE_COULUM_FILE);
        else
           return removeById(id);
    }

    @Override
    public boolean createOrUpdate(Column entity) {
        Map<String,Object>  columnMap= new HashMap<>();
        int len=entity.getColumnCode().length()+2;
        columnMap.put("columnCode",entity.getColumnCode());

        columnMap.put("sortOrder",len);
        int count=columnMapper.selectColumnCount(columnMap);
        int temp=count+1;
        String columnlen="";
        if (temp>=10){
            columnlen=entity.getColumnCode()+String.valueOf(temp);
        }
        else {
            columnlen=entity.getColumnCode()+"0"+String.valueOf(temp);

        }
        entity.setColumnCode(columnlen);
        return saveOrUpdate(entity);



    }

    @Override
    public List<Column> listByParentCode(String code) {
        return columnMapper.listByParentCode(code);
    }

    @Override
    public List<Long> listIdsByParentId(Long id) {
        List<Column> list = columnMapper.selectTreeByParentId(id);
        List<Long> result = new ArrayList<Long>();
        result.add(id);
        deep(list,result);
        return result;
    }

    /**
     * 递归获取栏目id集合
     * */
    private void deep(List<Column> source, List<Long> target){
        source.forEach(i->{
            target.add(i.getId());
            if(i.getChildren() !=null){
                deep(i.getChildren(),target);
            }
        });

    }


    /**
     *栏目移动
     * @param目标栏目id ,目标栏目上级id,目标层级关秀
     * @return
     * */
    @Override
    public int  MoveColumn(Map columnentity) {
        try {
            Map<String, Object> columnMap = new HashMap<>();
            int len = columnentity.get("columnCode").toString().length() + 2;
            columnMap.put("columnCode", columnentity.get("columnCode"));

            columnMap.put("sortOrder", len);
            int count = columnMapper.selectColumnCount(columnMap);
            int temp = count + 1;
            String columnlen = "";
            if (temp >= 10) {
                columnlen = columnentity.get("columnCode") + String.valueOf(temp);
            } else {
                columnlen = columnentity.get("columnCode") + "0" + String.valueOf(temp);

            }


            Map<String, Object> moveColumnMap = new HashMap<>();
            moveColumnMap.put("parentId", (Long) columnentity.get("targertid"));
            moveColumnMap.put("columnCode", columnlen);
            moveColumnMap.put("id", columnentity.get("sourceid"));

            return columnMapper.MoveColumn(moveColumnMap);

        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }


    }

}
