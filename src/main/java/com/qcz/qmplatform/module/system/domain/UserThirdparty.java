package com.qcz.qmplatform.module.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 * 用户和第三方的绑定关系
 * </p>
 *
 * @author quchangzhong
 * @since 2021-11-18
 */
@TableName("sys_user_thirdparty")
public class UserThirdparty implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId("user_id")
    private String userId;

    /**
     * 第三方接入用户唯一id
     */
    @TableField("thirdparty_id")
    private String thirdpartyId;

    /**
     * 接入类型
     */
    @TableField("access_type")
    private String accessType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getThirdpartyId() {
        return thirdpartyId;
    }

    public void setThirdpartyId(String thirdpartyId) {
        this.thirdpartyId = thirdpartyId;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    @Override
    public String toString() {
        return "UserThirdparty{" +
                "userId=" + userId +
                ", thirdpartyId=" + thirdpartyId +
                ", accessType=" + accessType +
                "}";
    }
}
