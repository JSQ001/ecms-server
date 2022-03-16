package com.eicas.cms.component;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
* 1、向认证服务发送请求获取授权码
* 2、校验授权码（获取用户信息）
* 3、创建本地会话（生成token）
* */

@Component
@Slf4j
@Getter
@Setter
public class ClientAuthenticate {

    private String tokenInfo;

    @Value("${oauth.server}")
    private String oauthServer;

    @Value("${oauth.clientId}")
    private String clientId;

    @Value("${oauth.clientSecret}")
    private String clientSecret;

    private String redirectUrl;

    /**
     * 校验令牌（获取用户信息）
     * */
    public String getAccessTokenByCode(String code){
        String url = String.format("%s/oauth/token.do?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s", oauthServer, clientId, clientSecret,code);
        try{
            String jwt = postHttp(url);
            log.info(jwt);
//            Gson gson = new Gson();
//            @SuppressWarnings("unchecked")
//            Map<String, Object> jwtmap = gson.fromJson(jwt, Map.class);
            tokenInfo = jwt;
            ///todo 生成客户端自己的token
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return tokenInfo;
    }

    private String postHttp(String url){
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response;
        try {
            response = client.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            return EntityUtils.toString(responseEntity);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


}
