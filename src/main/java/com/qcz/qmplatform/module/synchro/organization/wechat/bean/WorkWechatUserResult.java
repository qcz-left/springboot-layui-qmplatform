package com.qcz.qmplatform.module.synchro.organization.wechat.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 企业微信获取d用户结果
 */
@Data
@Accessors(chain = true)
public class WorkWechatUserResult implements Serializable {

    /**
     * 成员UserID
     */
    private String userid;

    /**
     * 成员名称
     */
    private String name;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 性别。0表示未定义，1表示男性，2表示女性。
     */
    private String gender = "0";

    /**
     * 邮箱
     */
    private String email;

    /**
     * 企业邮箱
     */
    private String biz_mail;
}
