package com.eicas.ecms.controller;

import com.eicas.ecms.annotation.ResponseResult;
import com.eicas.ecms.entity.Permission;
import com.eicas.ecms.service.IPermissionService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
* <p>
* 权限表 前端控制器
* </p>
*
* @author jsq
* @since 2021-11-29
*/
@Api(tags = "权限表")
@ResponseResult()
@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    @Resource
    private IPermissionService iPermissionService;

    @ApiOperation(value = "权限表分页列表", response = Permission.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "Permission.class", required = true),
        @ApiImplicitParam(name = "page", value = "页面", dataType = "Long"),
        @ApiImplicitParam(name = "size", value = "页面数据量", dataType = "Long"),
    })
    @GetMapping(value = "/page")
    public Page<Permission> searchPages(Permission entity, Page<Permission> page) {
        return iPermissionService.page(entity,page);
    }

    @ApiOperation(value = "权限表新增/修改")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "Permission.class", required = true),
    })
    @PostMapping(value = "/createOrUpdate")
    public boolean createOrUpdate(@RequestBody Permission entity) {
        return iPermissionService.saveOrUpdate(entity);
    }

    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true)})
    @DeleteMapping("/delete/{id}")
    public boolean physicalDelete(@PathVariable(value="id") String id){
        return iPermissionService.removeById(id);
    }

    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true)})
    @PostMapping("/delete/{id}")
    public boolean logicalDelete(@PathVariable(value="id") String id){
        return iPermissionService.update(new UpdateWrapper<Permission>().eq("id",id).set("is_deleted", 1));
    }

}
