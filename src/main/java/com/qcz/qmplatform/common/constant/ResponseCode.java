package com.qcz.qmplatform.common.constant;

/**
 * 响应码
 */
public enum ResponseCode {

    /**
     * 成功
     */
    SUCCESS(200),
    /**
     * 失败
     */
    ERROR(400),
    /**
     * 未认证
     */
    UNAUTHORIZED(401),
    /**
     * 已认证，会话过期
     */
    AUTHORIZED_EXPIRE(402),
    /**
     * 没有权限
     */
    PERMISSION_DENIED(403),
    /**
     * 接口不存在
     */
    NOT_FOUND(404),
    /**
     * 数据备份恢复成功
     */
    DATA_BAK_RECOVER(405),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500);

    private final int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
