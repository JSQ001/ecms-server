package com.eicas.ecms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 菜单角色权限表
 * </p>
 *
 * @author jsq
 * @since 2021-11-12
 */
@Getter
@Setter
@TableName("tb_menu_role")
@ApiModel(value = "MenuRole对象", description = "菜单角色权限表")
public class MenuRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("角色id")
    @TableField("role_id")
    private String roleId;

    @ApiModelProperty("菜单id")
    @TableField("menu_id")
    private String menuId;


}
