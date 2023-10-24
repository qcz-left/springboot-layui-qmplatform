package com.qcz.qmplatform.module.business.system.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CurrentUserInfoDTO implements Serializable {

    /**
     * 我的角色（多个,号隔开）
     */
    private String roleName;

    /**
     * 登录名
     */
    private String loginname;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 性别
     */
    private String userSex;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String emailAddr;

    /**
     * 备注
     */
    private String remark;

}
