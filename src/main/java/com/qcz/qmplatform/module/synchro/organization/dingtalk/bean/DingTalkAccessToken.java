package com.qcz.qmplatform.module.synchro.organization.dingtalk.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 钉钉 access_token
 */
@Data
@Accessors(chain = true)
public class DingTalkAccessToken implements Serializable {

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
