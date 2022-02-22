package com.eicas.ecms.dto;

import com.eicas.ecms.entity.RolePermission;
import lombok.Data;

import java.util.List;

@Data
public class RoleAssignPermissionParams {

    private List<String> delList;
    private List<RolePermission> addList;
}
