package com.qcz.qmplatform.module.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 * 操作日志实体
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-06
 */
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
    private Timestamp operateTime;

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

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public String getOperateModule() {
        return operateModule;
    }

    public void setOperateModule(String operateModule) {
        this.operateModule = operateModule;
    }

    public String getOperateUserId() {
        return operateUserId;
    }

    public void setOperateUserId(String operateUserId) {
        this.operateUserId = operateUserId;
    }

    public String getOperateUserName() {
        return operateUserName;
    }

    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Integer getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(Integer operateStatus) {
        this.operateStatus = operateStatus;
    }

    public String getExpMsg() {
        return expMsg;
    }

    public void setExpMsg(String expMsg) {
        this.expMsg = expMsg;
    }

    public Timestamp getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Timestamp operateTime) {
        this.operateTime = operateTime;
    }

    @Override
    public String toString() {
        return "OperateLog{" +
                "logId=" + logId +
                ", operateType=" + operateType +
                ", operateModule=" + operateModule +
                ", operateUserId=" + operateUserId +
                ", operateUserName=" + operateUserName +
                ", operateTime=" + operateTime +
                ", requestUrl=" + requestUrl +
                ", description=" + description +
                ", ipAddr=" + ipAddr +
                ", operateStatus=" + operateStatus +
                ", expMsg=" + expMsg +
                "}";
    }
}
