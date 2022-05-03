package com.eicas.security.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.common.ResultData;
import com.eicas.security.entity.AuthPermit;
import com.eicas.security.pojo.param.PermitQueryParam;
import com.eicas.security.service.IAuthPermitService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 权限信息表 前端控制器
 *
 * @author osnudt
 * @since 2022-04-23
 */
@RestController
@RequestMapping("/api/auth/permit")
public class AuthPermitController {

    @Resource
    private IAuthPermitService authPermitService;

    @ApiOperation("添加权限资源")
    @PostMapping(value = "/create")
    public ResultData create(@RequestBody AuthPermit permit) {
        if (authPermitService.save(permit)) {
            return ResultData.success("创建权限资源成功");
        } else {
            return ResultData.failed("创建权限资源失败");
        }
    }

    @ApiOperation("修改权限资源")
    @PostMapping(value = "/update/{id}")
    public ResultData update(@RequestBody AuthPermit permit) {
        if (authPermitService.saveOrUpdate(permit)) {
            return ResultData.success("修改权限资源成功");
        } else {
            return ResultData.failed("修改权限资源失败");
        }
    }

    @ApiOperation("根据ID获取资源详情")
    @GetMapping(value = "/{id}")
    public ResultData<AuthPermit> getPermitById(@PathVariable Long id) {
        return ResultData.success(authPermitService.getById(id));
    }

    @ApiOperation("根据ID删除权限资源")
    @PostMapping(value = "/delete/{id}")
    public ResultData delete(@PathVariable Long id) {
        if (authPermitService.removeById(id)) {
            return ResultData.success("删除权限资源成功");
        } else {
            return ResultData.failed("删除权限资源失败");
        }
    }

    @ApiOperation("分页模糊查询权限资源")
    @GetMapping(value = "/list")
    public ResultData<Page<AuthPermit>> list(@RequestBody PermitQueryParam param,
                                             @RequestParam(value = "current", defaultValue = "1") Integer current,
                                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResultData.success(authPermitService.listPermits(param, current, size));
    }

    @ApiOperation("查询所有权限资源")
    @GetMapping(value = "/list-all")
    public ResultData<Page<AuthPermit>> listAll() {
        return ResultData.success(authPermitService.listAll());
    }

}
