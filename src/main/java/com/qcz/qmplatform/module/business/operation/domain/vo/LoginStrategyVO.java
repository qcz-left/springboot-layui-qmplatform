package com.qcz.qmplatform.module.business.operation.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 登录策略
 */
@Data
@Accessors(chain = true)
public class LoginStrategyVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 策略开关（0：关；1：开）
     */
    private int enable;

    /**
     * 登录错误超过多少次时需要填写验证码
     */
    private int codeAtErrorTimes;

    /**
     * 密码认证失败超过多少次时锁定账号
     */
    private int lockAtErrorTimes;

}
