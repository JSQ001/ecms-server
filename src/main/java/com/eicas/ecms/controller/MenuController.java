package com.eicas.ecms.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.ecms.annotation.ResponseResult;
import com.eicas.ecms.dto.MenuAssignRoleParams;
import com.eicas.ecms.entity.Role;
import com.eicas.ecms.entity.Menu;
import com.eicas.ecms.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author jsq
 * @since 2021-11-29
 */
@Api(tags = "菜单表")
@ResponseResult()
@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Resource
    private IMenuService iMenuService;

    @ApiOperation(value = "菜单表分页列表", response = Menu.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "Menu.class", required = true),
            @ApiImplicitParam(name = "page", value = "页面", dataType = "Long"),
            @ApiImplicitParam(name = "size", value = "页面数据量", dataType = "Long"),
    })
    @GetMapping(value = "/page")
    public Page<Menu> searchPages(Menu entity, Page<Menu> page) {
        return iMenuService.page(entity,page);
    }

    @ApiOperation(value = "菜单表新增/修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "Menu.class", required = true),
    })
    @PostMapping(value = "/createOrUpdate")
    public boolean createOrUpdate(@RequestBody Menu entity) {
        return iMenuService.saveOrUpdate(entity);
    }

    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true)})
    @DeleteMapping("/delete/{id}")
    public boolean physicalDelete(@PathVariable(value="id") String id){
        return iMenuService.removeById(id);
    }

    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true)})
    @PostMapping("/delete/{id}")
    public boolean logicalDelete(@PathVariable(value="id") String id){
        return iMenuService.update(new UpdateWrapper<Menu>().eq("id",id).set("is_deleted", 1));
    }
    @ApiOperation(value = "菜单分配角色", response = Role.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "权限集合", paramType="body", dataType = "Role.class", required = true),
    })
    @PostMapping(value = "/assignRole")
    public boolean assignRole(@RequestBody MenuAssignRoleParams params) {
        return iMenuService.menuAssignRole(params);
    }

}
