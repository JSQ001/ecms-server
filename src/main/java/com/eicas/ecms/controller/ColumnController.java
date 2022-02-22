package com.eicas.ecms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.ecms.annotation.ResponseResult;
import com.eicas.ecms.entity.Column;
import com.eicas.ecms.entity.ColumnType;
import com.eicas.ecms.pojo.ColumnPojo;
import com.eicas.ecms.service.IColumnService;
import com.eicas.ecms.service.IColumnTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 栏目表 前端控制器
 * </p>
 *
 * @author jsq
 * @since 2021-11-29
 */
@Api(tags = "栏目表")
@ResponseResult()
@RestController
@RequestMapping("/api/column")
public class ColumnController {

    @Resource
    private IColumnService iColumnService;

    @Resource
    private IColumnTypeService columnTypeService;

    @ApiOperation(value = "栏目表分页列表", response = Column.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "Column.class", required = true),
            @ApiImplicitParam(name = "page", value = "页面", dataType = "Long"),
            @ApiImplicitParam(name = "size", value = "页面数据量", dataType = "Long"),
    })
    @GetMapping(value = "/page")
    public Page<Column> searchPages(ColumnPojo entity, Page<Column> page,String cloumnType) {
        //依据栏目类型来搜索
        if (cloumnType != null) {
            ColumnType typeName = columnTypeService.getOne(new QueryWrapper<ColumnType>().eq("column_type_name", cloumnType));
            entity.setTypeId(typeName.getId());
        }
        return iColumnService.page(entity, page);
//        Column columnName = iColumnService.getOne(new QueryWrapper<Column>().eq("column_name", entity.getColumnName()));
//        List<Column> childrenColumn = iColumnService.list(new QueryWrapper<Column>().eq("parent_id", columnName.getId()));
//        columnName.setChildren(childrenColumn);
//        ArrayList<Column> columns = new ArrayList<>();
//        columns.add(columnName);
//        columnPage.setRecords(columns);
//         columnPage;
    }

    @ApiOperation(value = "栏目表新增/修改")
    @ApiImplicitParams({
             @ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "Column.class", required = true),
    })
    @PostMapping(value = "/createOrUpdate")
    public boolean createOrUpdate(@RequestBody Column entity) {
        return iColumnService.saveOrUpdate(entity);
    }

    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true)})
    @DeleteMapping("/delete/{id}")
    public boolean physicalDelete(@PathVariable(value="id") String id){
        return iColumnService.removeById(id);
    }

    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true)})
    @PostMapping("/delete/{id}")
    public boolean logicalDelete(@PathVariable(value="id") String id){
        return iColumnService.update(new UpdateWrapper<Column>().eq("id",id).set("is_deleted", 1));
    }

}
