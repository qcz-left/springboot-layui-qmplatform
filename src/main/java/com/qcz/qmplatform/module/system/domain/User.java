package com.qcz.qmplatform.module.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 *
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private String id;

    /**
     * 密码
     */
    @TableField("password")
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[\\S]{5,12}$", message = "密码必须5到12位，且不能出现空格")
    private String password;

    /**
     * 用户名
     */
    @TableField("username")
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 登录名
     */
    @TableField("loginname")
    @NotBlank(message = "登录户名不能为空")
    private String loginname;

    /**
     * 用户性别
     */
    @TableField("user_sex")
    private String userSex;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Timestamp createTime;

    /**
     * 创建人id
     */
    @TableField("create_user_id")
    private String createUserId;

    /**
     * 手机号码
     */
    @TableField("phone")
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    /**
     * 邮箱地址
     */
    @TableField("email_addr")
    private String emailAddr;

    /**
     * 邮箱地址
     */
    @TableField("locked")
    private int locked;

    /**
     * 头像文件路径
     */
    @TableField("photo_path")
    private String photoPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", loginname='" + loginname + '\'' +
                ", userSex='" + userSex + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", createUserId='" + createUserId + '\'' +
                ", phone='" + phone + '\'' +
                ", emailAddr='" + emailAddr + '\'' +
                ", locked=" + locked +
                ", photoPath='" + photoPath + '\'' +
                '}';
    }
}
