package com.qcz.qmplatform.module.business.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 操作日志实体
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-06
 */
@Data
@Accessors(chain = true)
@TableName("sys_operate_log")
public class OperateLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志id，主键唯一标识
     */
    @TableId("log_id")
    private String logId;

    /**
     * 操作类型（-1：退出系统，1：进入系统，2：查询，3：新增，4：修改，5：删除） 需要其他类型，请自添加并注释
     */
    @TableField("operate_type")
    private Integer operateType;

    /**
     * 操作模块
     */
    @TableField("operate_module")
    private String operateModule;

    /**
     * 操作人ID
     */
    @TableField("operate_user_id")
    private String operateUserId;

    /**
     * 操作人名称
     */
    @TableField("operate_user_name")
    private String operateUserName;

    /**
     * 操作时间
     */
    @TableField("operate_time")
    private LocalDateTime operateTime;

    /**
     * 请求路径
     */
    @TableField("request_url")
    private String requestUrl;

    /**
     * 描述内容
     */
    @TableField("description")
    private String description;

    /**
     * IP地址
     */
    @TableField("ip_addr")
    private String ipAddr;

    /**
     * 操作状态（0：失败，1：成功）
     */
    @TableField("operate_status")
    private Integer operateStatus;

    /**
     * 异常信息
     */
    @TableField("exp_msg")
    private String expMsg;

}
