package com.qcz.qmplatform.module.business.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户和第三方的绑定关系
 * </p>
 *
 * @author quchangzhong
 * @since 2021-11-18
 */
@Data
@Accessors(chain = true)
@TableName("sys_user_thirdparty")
public class UserThirdparty implements Serializable {

    /**
     * 用户id
     */
    @TableField("user_id")
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

}
