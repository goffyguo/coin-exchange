package com.guofei.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * @Author: GuoFei
 * @Date: 2021/07/09/13:51
 * @Description: 
 */
/**
    * 角色权限配置
    */
@ApiModel(value="com-guofei-domain-SysRolePrivilege")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_role_privilege")
public class SysRolePrivilege {
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private Long id;

    @TableField(value = "role_id")
    @ApiModelProperty(value="")
    private Long roleId;

    @TableField(value = "privilege_id")
    @ApiModelProperty(value="")
    private Long privilegeId;
}