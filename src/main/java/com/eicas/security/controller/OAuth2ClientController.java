package com.eicas.security.controller;

import com.eicas.security.pojo.dto.SocialLoginDTO;
import com.eicas.security.service.IOAuth2ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一认证中心接入适配
 *
 * @author osnudt
 * @since 2022/4/30
 */

@Slf4j
@RestController
@RequestMapping("/oauth2-client/")
public class OAuth2ClientController {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    IOAuth2ClientService oauth2clientService;

    @Value("${ecms.auth-server}")
    private String AUTH_SVR;
    /*
     * 向此地址请求身份认证，颁发授权码
     */
    @Value("${ecms.authorization-url}")
    private String AUTHORIZATION_URL;

    /*
     * 携带授权码向此地址请求访问令牌
     */
    @Value("${ecms.token-url}")
    private String TOKEN_URL;

    @Value("${ecms.client-id}")
    private String CLIENT_ID;

    @Value("${ecms.client-secret}")
    private String CLIENT_SECRET;

    @Value("${ecms.signoff-uri}")
    private String SIGNOFF_URI;

    @RequestMapping("/entry")
    public ModelAndView entry() {
        // TODO
        return new ModelAndView("redirect:" + AUTHORIZATION_URL);
    }

    /**
     * 回调端点
     * 接收认证服务器发来的授权码，并换取令牌
     *
     * @param code 授权码
     * @return
     */
    @RequestMapping("/callback")
    public String callback(@RequestParam String code) {
        String data;
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", CLIENT_ID);
        params.put("client_secret", CLIENT_SECRET);
        params.put("code", code);

        data = restTemplate.getForObject(TOKEN_URL, String.class, params);

        SocialLoginDTO loginDTO = oauth2clientService.parseResponseData(data);
        oauth2clientService.login(loginDTO);
        return data;
    }

    /**
     * 登出, 调用认证中心的登出接口, 使用户退出登录状态.
     */
    @RequestMapping("/sign-off")
    public String signoff() {
        // TODO
        return String.format("redirect:%s?clientid=%s&secret=%s&redirectUri=%s",
                SIGNOFF_URI, CLIENT_ID, CLIENT_SECRET, SIGNOFF_URI);
    }
}
