package com.qcz.qmplatform.module.business.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 第三方应用参数配置信息
 * </p>
 *
 * @author quchangzhong
 * @since 2022-01-26
 */
@Data
@Accessors(chain = true)
@TableName("sys_thirdparty_app")
public class ThirdpartyApp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableField("id")
    private String id;

    /**
     * 第三方系统名称
     */
    @TableField("name")
    private String name;

    /**
     * 系统应用id
     */
    @TableField("app_key")
    private String appKey;

    /**
     * 应用秘钥
     */
    @TableField("app_secret")
    private String appSecret;

    /**
     * 描述
     */
    @TableField("remark")
    private String remark;

    /**
     * 状态（已废除）
     */
    @Deprecated
    @TableField(value = "status", exist = false)
    private int status;

}
