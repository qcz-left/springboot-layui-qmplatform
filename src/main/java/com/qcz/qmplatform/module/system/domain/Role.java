package com.qcz.qmplatform.module.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@Data
@Accessors(chain = true)
@TableName("sys_role")
public class Role implements Serializable {

    @TableId("role_id")
    private String roleId;

    @TableField("role_name")
    private String roleName;

    @TableField("role_sign")
    private String roleSign;

    @TableField("remark")
    private String remark;

}
