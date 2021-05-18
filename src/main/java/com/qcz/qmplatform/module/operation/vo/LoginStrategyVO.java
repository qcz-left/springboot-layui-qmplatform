package com.qcz.qmplatform.module.operation.vo;

import java.io.Serializable;

/**
 * 登录策略
 */
public class LoginStrategyVO implements Serializable {

    private static final long serialVersionUID = -4297394148041529088L;

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

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getCodeAtErrorTimes() {
        return codeAtErrorTimes;
    }

    public void setCodeAtErrorTimes(int codeAtErrorTimes) {
        this.codeAtErrorTimes = codeAtErrorTimes;
    }

    public int getLockAtErrorTimes() {
        return lockAtErrorTimes;
    }

    public void setLockAtErrorTimes(int lockAtErrorTimes) {
        this.lockAtErrorTimes = lockAtErrorTimes;
    }
}
