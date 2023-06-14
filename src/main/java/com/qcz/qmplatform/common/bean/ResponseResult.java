package com.qcz.qmplatform.common.bean;

import com.qcz.qmplatform.common.constant.ResponseCode;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 响应结果
 */
@Data
@Accessors(chain = true)
@ToString
public class ResponseResult<T> {

    private int code;

    private String msg;

    private T data;

    public ResponseResult() {

    }

    public ResponseResult(ResponseCode code, String msg, T data) {
        this.code = code.code();
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseResult<T> ok(String msg, T data) {
        return new ResponseResult<>(ResponseCode.SUCCESS, msg, data);
    }

    public static <T> ResponseResult<T> ok(T data) {
        return new ResponseResult<>(ResponseCode.SUCCESS, null, data);
    }

    public static ResponseResult<?> ok(String msg) {
        return new ResponseResult<>(ResponseCode.SUCCESS, msg, null);
    }

    public static ResponseResult<?> ok() {
        return new ResponseResult<>(ResponseCode.SUCCESS, null, null);
    }

    public static <T> ResponseResult<T> error(String msg, T data) {
        return new ResponseResult<>(ResponseCode.ERROR, msg, data);
    }

    public static ResponseResult<?> error(String msg) {
        return new ResponseResult<>(ResponseCode.ERROR, msg, null);
    }

    public static <T> ResponseResult<T> error(T data) {
        return new ResponseResult<>(ResponseCode.ERROR, null, data);
    }

    public static ResponseResult<?> error() {
        return new ResponseResult<>(ResponseCode.ERROR, null, null);
    }

    public static ResponseResult<?> newInstance(boolean success) {
        return new ResponseResult<>(success ? ResponseCode.SUCCESS : ResponseCode.ERROR, null, null);
    }

    public boolean isOk() {
        return code == ResponseCode.SUCCESS.code();
    }

}
