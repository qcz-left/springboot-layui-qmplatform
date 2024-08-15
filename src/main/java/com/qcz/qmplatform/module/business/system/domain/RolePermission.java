package com.qcz.qmplatform.module.business.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@Data
@Accessors(chain = true)
@TableName("sys_role_permission")
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("role_id")
    private String roleId;

    @TableField("permission_id")
    private String permissionId;

}
