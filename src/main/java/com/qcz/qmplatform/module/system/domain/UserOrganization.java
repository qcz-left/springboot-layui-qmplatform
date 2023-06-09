package com.qcz.qmplatform.module.system.domain;

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
@TableName("sys_user_organization")
public class UserOrganization implements Serializable {

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 机构id
     */
    @TableField("organization_id")
    private String organizationId;

}
