package com.eicas.security.service.impl;

import com.eicas.security.entity.AuthUser;
import com.eicas.security.pojo.dto.SocialLoginDTO;
import com.eicas.security.pojo.dto.SocialTokenDTO;
import com.eicas.security.pojo.dto.SocialUserDTO;
import com.eicas.security.service.IAuthUserService;
import com.eicas.security.service.IOAuth2ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author osnudt
 * @since 2022/5/1
 */

@Service
@Slf4j
public class OAuth2ClientServiceImpl implements IOAuth2ClientService {

    @Resource
    IAuthUserService authUserService;

    @Override
    public Boolean login(SocialLoginDTO loginDTO) {
        AuthUser authUser = this.getAuthUser(loginDTO);
        if (authUser == null) {
            authUser = saveSocialUser(getSocialUser(loginDTO));
        }
        UserDetails userDetails = authUserService.loadUserByUsername(authUser.getUsername());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return null;
    }

    @Override
    public SocialTokenDTO getSocialToken(String data) {
        return null;
    }

    @Override
    public SocialUserDTO getSocialUser(SocialLoginDTO loginDTO) {
        // TODO
        return new SocialUserDTO();
    }

    @Override
    public AuthUser getAuthUser(SocialLoginDTO tokenDTO) {
        return authUserService.getByUsername(tokenDTO.getUsername());
    }

    @Override
    public AuthUser saveSocialUser(SocialUserDTO userDTO) {
        return null;
    }

    /**
     * 解析认证中心返回数据
     */
    @Override
    public SocialLoginDTO parseResponseData(String data) {
        SocialLoginDTO loginDTO = new SocialLoginDTO();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(data);
            String accessToken = jsonNode.get("access_token").asText();
            String tokenType = jsonNode.get("token_type").asText();
            String refreshToken = jsonNode.get("refresh_token").asText();
            Long expired = jsonNode.get("expires_in").asLong();

            String scope = jsonNode.get("scope").asText();
            JsonNode userInfo = jsonNode.get("userinfo");
            Long uuid = userInfo.get("id").asLong();

            JsonNode tokenInfo = jsonNode.get("tokeninfo");
            String username = tokenInfo.get("usercode").asText();
            String sysName = tokenInfo.get("username").asText();
            String appId = tokenInfo.get("app").asText();
            String appType = tokenInfo.get("apptype").asText();
            String userRole = tokenInfo.get("userrole").asText();
            UUID jti = UUID.fromString(tokenInfo.get("jti").asText());

            loginDTO.setUuid(uuid);
            loginDTO.setUsername(username);
            loginDTO.setAppId(appId);
            loginDTO.setExpired(expired);
            loginDTO.setAppType(appType);
            loginDTO.setJti(jti);
            loginDTO.setAccessToken(accessToken);
            loginDTO.setTokenType(tokenType);
            loginDTO.setRefreshToken(refreshToken);
            loginDTO.setScope(scope);
            loginDTO.setRealname(sysName);
            loginDTO.setRoles(userRole);
        } catch (JsonProcessingException ex) {
            loginDTO = null;
            log.error(ex.getMessage());
        }
        return loginDTO;
    }

}
