package com.eicas.ecms.dto;

import com.eicas.ecms.entity.MenuRole;
import lombok.Data;

import java.util.List;

@Data
public class MenuAssignRoleParams {
    private List<String> delList;
    private List<MenuRole> addList;
}
