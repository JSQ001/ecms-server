package com.eicas.cms.controller;

import com.eicas.cms.component.ClientAuthenticate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/oauth")
public class OauthController {

    @Resource
    private ClientAuthenticate clientAuthenticate;


    /**
     * 认证中心回调地址并接受授权码, 根据认证中心返回的授权码获取令牌.
     */
    @GetMapping("/getAccessToken")
    public String getAccessToken(String code) {
        return clientAuthenticate.getAccessTokenByCode(code);
    }
    @GetMapping("/loginOut")
    public boolean loginOut() {
        clientAuthenticate.setTokenInfo(null);
        return true;
    }



}
