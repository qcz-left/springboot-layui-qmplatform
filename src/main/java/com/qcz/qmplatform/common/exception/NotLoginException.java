package com.qcz.qmplatform.common.exception;

/**
 * 未登录异常
 */
public class NotLoginException extends RuntimeException {

    public NotLoginException(String message) {
        super(message);
    }

    public NotLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
