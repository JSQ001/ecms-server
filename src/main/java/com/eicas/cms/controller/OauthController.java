package com.eicas.cms.controller;

import com.eicas.cms.component.ClientAuthenticate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@Controller
@RequestMapping("/oauth")
public class OauthController {

    @Resource
    private ClientAuthenticate clientAuthenticate;

    /**
     * 认证中心回调地址并接受授权码, 根据认证中心返回的授权码获取令牌.
     */
    @RequestMapping("/login")
    public String getAccessToken() {
        System.out.println(String.format("%s/oauth/authorize.do?response_type=code&client_id=%s",clientAuthenticate.getOauthServer(),clientAuthenticate.getClientId()));
        return String.format("redirect:%s/oauth/authorize.do?response_type=code&client_id=%s",clientAuthenticate.getOauthServer(),clientAuthenticate.getClientId());
    }


    @GetMapping("/loginOut")
    public String loginOut() {
        clientAuthenticate.setTokenInfo(null);
        return String.format("redirect:%s/oauth/signoff.do?clientid=%s&secret=%s&redirectUri=%s",
                clientAuthenticate.getOauthServer(),
                clientAuthenticate.getClientId(),
                clientAuthenticate.getClientSecret(),
                clientAuthenticate.getWebUrl()
        );
    }

}
