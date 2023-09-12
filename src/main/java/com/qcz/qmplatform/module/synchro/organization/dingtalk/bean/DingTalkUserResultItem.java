package com.qcz.qmplatform.module.synchro.organization.dingtalk.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 钉钉-获取用户列表返回的结果的用户列表
 */
@Data
@Accessors(chain = true)
public class DingTalkUserResultItem implements Serializable {

    /**
     * 用户的userId
     */
    private String userid;

    /**
     * 用户在当前开发者企业帐号范围内的唯一标识
     */
    private String unionid;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 员工邮箱
     */
    private String email;

    /**
     * 员工的企业邮箱
     */
    private String org_email;

    /**
     * 备注
     */
    private String remark;

}
