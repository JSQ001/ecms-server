package com.eicas.security.service;

import com.eicas.security.entity.AuthUser;
import com.eicas.security.pojo.dto.SocialLoginDTO;
import com.eicas.security.pojo.dto.SocialTokenDTO;
import com.eicas.security.pojo.dto.SocialUserDTO;

/**
 * @author osnudt
 * @since 2022/5/1
 */


public interface IOAuth2ClientService {

    public Boolean login(SocialLoginDTO loginData);


    /**
     * 获取第三方token信息
     *
     * @param data 数据
     * @return 第三方token信息
     */
    public SocialTokenDTO getSocialToken(String data);

    public SocialLoginDTO parseResponseData(String data);

    /**
     * 获取第三方用户信息
     *
     * @param tokenDTO 第三方token信息
     * @return 第三方用户信息
     */
    public abstract SocialUserDTO getSocialUser(SocialLoginDTO tokenDTO);

    /**
     * 从用户信息表中获取用户信息
     *
     * @param loginDTO 第三方认证信息
     * @return 用户信息对象
     */
    public AuthUser getAuthUser(SocialLoginDTO loginDTO);

    /**
     * 新增用户信息
     *
     * @param userDTO SocialUserDTO对象
     * @return  用户信息
     */
    public AuthUser saveSocialUser(SocialUserDTO userDTO);
}
