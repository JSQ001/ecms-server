package com.eicas.ecms.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.ecms.annotation.ResponseResult;
import com.eicas.ecms.entity.UserInfo;
import com.eicas.ecms.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author jsq
 * @since 2021-11-29
 */
@Api(tags = "用户信息表")
@ResponseResult()
@RestController
@RequestMapping("/api/user-info")
public class UserInfoController {

    @Resource
    private IUserInfoService iUserInfoService;

    @ApiOperation(value = "用户信息表分页列表", response = UserInfo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "UserInfo.class", required = true),
            @ApiImplicitParam(name = "page", value = "页面", dataType = "Long"),
            @ApiImplicitParam(name = "size", value = "页面数据量", dataType = "Long"),
    })
    @GetMapping(value = "/page")
    public Page<UserInfo> searchPages(UserInfo entity, Page<UserInfo> page) {
        return iUserInfoService.page(entity,page);
    }

    @ApiOperation(value = "用户信息表新增/修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "UserInfo.class", required = true),
    })
    @PostMapping(value = "/createOrUpdate")
    public boolean createOrUpdate(@RequestBody UserInfo entity) {
        return iUserInfoService.saveOrUpdate(entity);
    }

    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true)})
    @DeleteMapping("/delete/{id}")
    public boolean physicalDelete(@PathVariable(value="id") String id){
        return iUserInfoService.removeById(id);
    }

    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true)})
    @PostMapping("/delete/{id}")
    public boolean logicalDelete(@PathVariable(value="id") String id){
        return iUserInfoService.update(new UpdateWrapper<UserInfo>().eq("id",id).set("is_deleted", 1));
    }

}
