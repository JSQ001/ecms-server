package com.eicas.cms.controller;


import com.eicas.cms.component.ClientAuthenticate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/oauth")
public class OauthRestController {

    @Resource
    private ClientAuthenticate clientAuthenticate;


    @GetMapping("/getToken")
    public String getToken (String code) {
        return clientAuthenticate.getAccessTokenByCode(code);
    }
}
