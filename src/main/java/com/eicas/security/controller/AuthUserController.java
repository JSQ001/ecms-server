package com.eicas.security.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.common.ResultData;
import com.eicas.security.entity.AuthRole;
import com.eicas.security.entity.AuthUser;
import com.eicas.security.pojo.param.UserPasswordParam;
import com.eicas.security.service.IAuthUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;

/**
 * 系统账号前端控制器
 *
 * @author osnudt
 * @since 2022-04-23
 */
@RestController
@RequestMapping("/api/auth/user")
public class AuthUserController {
    @Resource
    IAuthUserService userService;

    @ApiOperation("添加用户")
    @PostMapping(value = "/create")
    public ResultData create(@RequestBody AuthUser user) {
        return ResultData.success(userService.save(user), "新增用户成功");
    }

    @ApiOperation("获取所有用户")
    @GetMapping(value = "/list-all")
    public List<AuthUser> listAll() {
        return userService.list();
    }

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @GetMapping(value = "/list")
    public Page<AuthRole> list(@RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "current", defaultValue = "1") Integer current,
                               @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return userService.listAuthUser(keyword, current, size);
    }

    @ApiOperation("修改指定用户信息")
    @PostMapping(value = "/update")
    public ResultData update(@RequestBody AuthUser user) {
        return ResultData.success(userService.saveOrUpdate(user));
    }

    @ApiOperation("修改指定用户密码")
    @PostMapping(value = "/change-password")
    public ResultData updatePassword(@Validated @RequestBody UserPasswordParam param) {
        int status = userService.changePassword(param);
        if (status > 0) {
            return ResultData.success(status);
        } else if (status == -1) {
            return ResultData.failed("提交参数不合法");
        } else if (status == -2) {
            return ResultData.failed("找不到该用户");
        } else if (status == -3) {
            return ResultData.failed("旧密码错误");
        } else {
            return ResultData.failed();
        }
    }

    @ApiOperation("删除指定用户信息")
    @PostMapping(value = "/delete/{userId}")
    public Boolean delete(@PathVariable Long userId) {
        return userService.removeById(userId);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping(value = "/current")
    public ResultData getCurrentUserInfo(Principal principal) {
        if (principal == null) {
            return ResultData.unauthorized(null);
        }
        String username = principal.getName();
        AuthUser user = userService.getByUsername(username);
        return ResultData.success(user);
    }

    @ApiOperation("给用户分配角色")
    @PostMapping(value = "/alloc-role")
    public ResultData allocRole(@RequestParam("userId") Long userId,
                                @RequestParam("roleIds") List<Long> roleIds) {
        userService.allocRole(userId, roleIds);
        return ResultData.success("给用户分配角色");
    }

    @ApiOperation("获取用户对应角色")
    @PostMapping(value = "/role")
    public List<AuthRole> listRole(@RequestParam("userId") Long userId) {
        return userService.listRolesById(userId);
    }

}
