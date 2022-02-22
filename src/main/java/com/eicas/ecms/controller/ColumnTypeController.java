package com.eicas.ecms.controller;

import com.eicas.ecms.entity.ColumnType;
import com.eicas.ecms.service.IColumnTypeService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.ecms.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;


/**
 * <p>
 * 栏目类型表 前端控制器
 * </p>
 *
 * @author jsq
 * @since 2021-11-29
 */
@Api(tags = "栏目类型表")
@ResponseResult()
@RestController
@RequestMapping("/api/column-type")
public class ColumnTypeController {

    @Resource
    private IColumnTypeService iColumnTypeService;

    @ApiOperation(value = "栏目类型表分页列表", response = ColumnType.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "ColumnType.class", required = true),
            @ApiImplicitParam(name = "page", value = "页面", dataType = "Long"),
            @ApiImplicitParam(name = "size", value = "页面数据量", dataType = "Long"),
    })
    @GetMapping(value = "/page")
    public Page<ColumnType> searchPages(ColumnType entity, Page<ColumnType> page) {
        return iColumnTypeService.page(entity,page);
    }

    @ApiOperation(value = "栏目类型表新增/修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "ColumnType.class", required = true),
    })
    @PostMapping(value = "/createOrUpdate")
    public boolean createOrUpdate(@RequestBody ColumnType entity) {
        return iColumnTypeService.saveOrUpdate(entity);
    }

    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true)})
    @DeleteMapping("/delete/{id}")
    public boolean physicalDelete(@PathVariable(value="id") String id){
        return iColumnTypeService.removeById(id);
    }

    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true)})
    @PostMapping("/delete/{id}")
    public boolean logicalDelete(@PathVariable(value="id") String id){
        return iColumnTypeService.update(new UpdateWrapper<ColumnType>().eq("id",id).set("is_deleted", 1));
    }

}

