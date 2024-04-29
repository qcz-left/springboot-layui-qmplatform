package com.qcz.qmplatform.module.business.system.domain.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 钉钉用户token信息
 */
@Data
public class DingTalkUserAccessToken implements Serializable {

    /**
     * 生成的accessToken
     */
    private String accessToken;

    /**
     * 生成的refresh_token。可以使用此刷新token，定期的获取用户的accessToken
     */
    private String refreshToken;

    /**
     * 超时时间，单位秒
     */
    private long expireIn;

    /**
     * 所选企业corpId
     */
    private String corpId;

}
