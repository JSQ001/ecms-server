package com.eicas.security.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.common.ResultData;
import com.eicas.security.entity.AuthPermit;
import com.eicas.security.entity.AuthRole;
import com.eicas.security.service.IAuthRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色信息前端控制器
 *
 * @author osnudt
 * @since 2022-04-23
 */
@RestController
@RequestMapping("/api/auth/role")
public class AuthRoleController {
    @Resource
    private IAuthRoleService roleService;

    @ApiOperation("添加角色")
    @PostMapping(value = "/create")
    public ResultData create(@RequestBody AuthRole role) {
        return ResultData.success(roleService.save(role), "");
    }

    @ApiOperation("修改角色")
    @PostMapping(value = "/update")
    public ResultData update(@RequestBody AuthRole role) {
        if (roleService.updateById(role)) {
            return ResultData.success(role, "");
        } else {
            return ResultData.failed();
        }
    }

    @ApiOperation("批量删除角色")
    @PostMapping(value = "/delete")
    public ResultData delete(@RequestParam("ids") List<Long> ids) {
        if (roleService.removeBatchByIds(ids)) {
            return ResultData.success("批量删除角色成功");
        } else {
            return ResultData.failed("批量删除角色失败");
        }
    }

    @ApiOperation("获取所有角色")
    @GetMapping(value = "/list-all")
    public ResultData<List<AuthRole>> listAll() {
        List<AuthRole> roleList = roleService.list();
        return ResultData.success(roleList);
    }

    @ApiOperation("根据角色名称分页获取角色列表")
    @GetMapping(value = "/list")
    public Page<AuthRole> list(@RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "current", defaultValue = "1") Integer current,
                               @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return roleService.listRole(keyword, current, size);
    }

    @ApiOperation("获取角色相关权限点")
    @GetMapping(value = "/list-permit/{id}")
    public ResultData<List<AuthPermit>> listPermit(@PathVariable Long id) {
        return ResultData.success(roleService.listPermit(id));
    }

    @ApiOperation("给角色分配权限点")
    @PostMapping(value = "/alloc-permit")
    @ResponseBody
    public ResultData allocPermit(@RequestParam Long roleId, @RequestParam List<Long> permitIds) {
        if (roleService.allocPermit(roleId, permitIds)) {
            return ResultData.success("给角色分配权限失败");
        } else {
            return ResultData.failed("给角色分配权限失败");
        }
    }

}
