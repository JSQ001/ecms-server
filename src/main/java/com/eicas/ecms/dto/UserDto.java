package com.eicas.ecms.dto;

import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String confirmPwd;
    private String userInfoId;
    private String phone;
    private String roleId;
    private String roleName;
    private String deleted;
}
