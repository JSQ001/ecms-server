package com.eicas.ecms.controller;

import com.eicas.ecms.annotation.ResponseResult;
import com.eicas.ecms.entity.User;
import com.eicas.ecms.dto.UserDto;
import com.eicas.ecms.service.IUserService;
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
 * 用户表 前端控制器
 * </p>
 *
 * @author jsq
 * @since 2021-10-28
 */
@Api(tags = "用户表")
@ResponseResult()
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private IUserService iUserService;

    @ApiOperation(value = "用户表分页列表", response = User.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "com.eicas.ecms.entity.User.class", required = true),
            @ApiImplicitParam(name = "page", value = "页面", dataType = "Long"),
            @ApiImplicitParam(name = "size", value = "页面数据量", dataType = "Long"),
    })
    @GetMapping(value = "/page")
    public Page<UserDto> searchPages(UserDto entity, Page<UserDto> page) {
        return iUserService.page(entity,page);
    }

    @ApiOperation(value = "用户表新增/修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "com.eicas.ecms.entity.User.class", required = true),
    })
    @PostMapping(value = "/createOrUpdate")
    public boolean createOrUpdate(@RequestBody UserDto entity) {
        return iUserService.mySaveOrUpdate(entity);
    }

    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true)})
    @DeleteMapping("/delete/{id}")
    public boolean physicalDelete(@PathVariable(value="id") String id){
        return iUserService.removeById(id);
    }

    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true)})
    @PostMapping("/delete/{id}")
    public boolean logicalDelete(@PathVariable(value="id") String id){
        return iUserService.update(new UpdateWrapper<User>().eq("id",id).set("deleted", 1));
    }

}
