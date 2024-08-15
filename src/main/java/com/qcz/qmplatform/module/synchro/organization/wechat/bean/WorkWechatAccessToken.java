package com.qcz.qmplatform.module.synchro.organization.wechat.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 企业微信 access_token
 */
@Data
@Accessors(chain = true)
public class WorkWechatAccessToken implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 生成的access_token
     */
    private String access_token;

    /**
     * access_token的过期时间，单位秒
     */
    private int expires_in;

    /**
     * 返回码描述
     */
    private String errmsg;

    /**
     * 返回码
     */
    private int errcode;
}
