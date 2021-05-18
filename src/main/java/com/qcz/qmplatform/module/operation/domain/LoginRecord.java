package com.qcz.qmplatform.module.operation.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 * 账号登录系统记录
 * </p>
 *
 * @author quchangzhong
 * @since 2021-05-17
 */
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
    private Timestamp lastLoginTime;

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

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getErrorTimes() {
        return errorTimes;
    }

    public void setErrorTimes(Integer errorTimes) {
        this.errorTimes = errorTimes;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "OpeLoginRecord{" +
                "recordId=" + recordId +
                ", loginName=" + loginName +
                ", errorTimes=" + errorTimes +
                ", lastLoginTime=" + lastLoginTime +
                ", lastLoginIp=" + lastLoginIp +
                ", remark=" + remark +
                "}";
    }
}
