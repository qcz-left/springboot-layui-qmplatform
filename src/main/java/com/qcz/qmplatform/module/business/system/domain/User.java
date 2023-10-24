package com.qcz.qmplatform.module.business.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qcz.qmplatform.common.anno.ExcelField;
import com.qcz.qmplatform.common.validation.Phone;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 *  用户
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@Data
@Accessors(chain = true)
@TableName("sys_user")
public class User implements Serializable {

    /**
     * 主键id
     */
    @TableId(value = "id")
    private String id;

    /**
     * 密码
     */
    @TableField("password")
    @NotBlank(message = "{pwd_not_blank}")
    @Pattern(regexp = "^[\\S]{5,12}$", message = "密码必须5到12位，且不能出现空格")
    private String password;

    /**
     * 用户名
     */
    @TableField("username")
    @ExcelField("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 登录名
     */
    @TableField("loginname")
    @ExcelField("登录名")
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
    @ExcelField("电话")
    @NotBlank(message = "手机号码不能为空")
    @Phone
    private String phone;

    /**
     * 邮箱地址
     */
    @TableField("email_addr")
    @ExcelField("邮箱")
    @NotBlank(message = "邮箱地址不能为空")
    @Email
    private String emailAddr;

    /**
     * 锁定状态（0：正常；1：锁定）
     */
    @TableField("locked")
    @Range(min = 0, max = 1)
    private int locked;

    /**
     * 头像文件路径
     */
    @TableField("photo_path")
    private String photoPath;

}
