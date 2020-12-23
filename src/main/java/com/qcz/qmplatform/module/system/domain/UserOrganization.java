package com.qcz.qmplatform.module.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@TableName("sys_user_organization")
public class UserOrganization implements Serializable {

private static final long serialVersionUID=1L;

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


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public String toString() {
        return "UserOrganization{" +
        "userId=" + userId +
        ", organizationId=" + organizationId +
        "}";
    }
}
