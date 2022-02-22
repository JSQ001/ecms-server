package com.eicas.ecms.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.ecms.annotation.ResponseResult;
import com.eicas.ecms.dto.RoleAssignPermissionParams;
import com.eicas.ecms.entity.Role;
import com.eicas.ecms.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
* <p>
* 角色表 前端控制器
* </p>
*
* @author jsq
* @since 2021-11-11
*/
@Api(tags = "角色表")
@ResponseResult()
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Resource
    private IRoleService iRoleService;

    @ApiOperation(value = "角色表分页列表", response = Role.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "Role.class", required = true),
        @ApiImplicitParam(name = "page", value = "页面", dataType = "Long"),
        @ApiImplicitParam(name = "size", value = "页面数据量", dataType = "Long"),
    })
    @GetMapping(value = "/page")
    public Page<Role> searchPages(Role entity, Page<Role> page) {
        return iRoleService.page(entity,page);
    }

    @ApiOperation(value = "角色表新增/修改")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "Role.class", required = true),
    })
    @PostMapping(value = "/createOrUpdate")
    public boolean createOrUpdate(@RequestBody Role entity) {
        return iRoleService.saveOrUpdate(entity);
    }


    @ApiOperation(value = "角色表分配权限", response = Role.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "权限集合", paramType="body", dataType = "Role.class", required = true),
    })
    @PostMapping(value = "/assignAuthority")
    public boolean assignAuthority(@RequestBody RoleAssignPermissionParams params) {
        return iRoleService.roleAssignAuthority(params);
    }

    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true)})
    @DeleteMapping("/delete/{id}")
    public boolean physicalDelete(@PathVariable(value="id") String id){
        return iRoleService.removeById(id);
    }

    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true)})
    @PostMapping("/delete/{id}")
    public boolean logicalDelete(@PathVariable(value="id") String id){
        return iRoleService.update(new UpdateWrapper<Role>().eq("id",id).set("deleted", 1));
    }

}
