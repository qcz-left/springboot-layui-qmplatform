package com.qcz.qmplatform.module.business.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qcz.qmplatform.common.validation.groups.Update;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
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
    @NotBlank(groups = Update.class)
    private String roleId;

    @TableField("role_name")
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    @TableField("role_sign")
    @NotBlank
    private String roleSign;

    @TableField("remark")
    private String remark;

}
