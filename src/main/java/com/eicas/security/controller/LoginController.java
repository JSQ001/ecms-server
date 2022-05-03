package com.eicas.security.controller;

import com.eicas.common.ResultData;
import com.eicas.security.entity.AuthUser;
import com.eicas.security.pojo.param.UserLoginParam;
import com.eicas.security.pojo.param.UserRegisterParam;
import com.eicas.security.service.IAuthUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author osnudt
 * @since 2022/4/27
 */

@RestController
@RequestMapping
public class LoginController {
    @Resource
    IAuthUserService authUserService;

    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    public ResultData<AuthUser> register(@Validated @RequestBody UserRegisterParam param) {
        AuthUser user = authUserService.register(param);
        if (user == null) {
            return ResultData.failed("用户注册失败");
        }
        return ResultData.success(user);
    }

    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/login")
    public ResultData login(@Validated @RequestBody UserLoginParam param) {
        boolean result = authUserService.login(param.getUsername(), param.getPassword());
        return result ? ResultData.success("用户认证成功") : ResultData.validateFailed("用户名或密码错误");
    }

    @ApiOperation(value = "用户退出登录")
    @PostMapping(value = "/logout")
    public ResultData logout() {
        return ResultData.success(null);
    }
}
