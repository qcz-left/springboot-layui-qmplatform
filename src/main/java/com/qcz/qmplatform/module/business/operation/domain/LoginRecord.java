package com.qcz.qmplatform.module.business.operation.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 账号登录系统记录
 * </p>
 *
 * @author quchangzhong
 * @since 2021-05-17
 */
@Data
@Accessors(chain = true)
@TableName("ope_login_record")
public class LoginRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录id
     */
    @TableId("record_id")
    private String recordId;

    /**
     * 登录名
     */
    @TableField("login_name")
    private String loginName;

    /**
     * 登录错误次数
     */
    @TableField("error_times")
    private Integer errorTimes;

    /**
     * 最近登录时间
     */
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    /**
     * 最近登录IP
     */
    @TableField("last_login_ip")
    private String lastLoginIp;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
