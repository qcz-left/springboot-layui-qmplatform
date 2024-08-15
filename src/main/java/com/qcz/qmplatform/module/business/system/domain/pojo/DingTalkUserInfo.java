package com.qcz.qmplatform.module.business.system.domain.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 钉钉用户信息
 */
@Data
public class DingTalkUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户的钉钉昵称
     */
    private String nick;
    /**
     * 头像URL
     */
    private String avatarUrl;
    /**
     * 用户的手机号
     */
    private String mobile;
    /**
     * 用户的openId
     */
    private String openId;
    /**
     * 用户的unionId
     */
    private String unionId;
    /**
     * 用户的个人邮箱
     */
    private String email;
    /**
     * 手机号对应的国家号
     */
    private String stateCode;
}
