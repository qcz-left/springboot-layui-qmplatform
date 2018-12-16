package com.qcz.qmplatform.common.message;

/**
 * 响应结果统一处理
 * @author quchangzhong
 * @time 2018年1月19日 下午8:17:30
 */
public class ResponseResult {

	private boolean isSuccess;

	private String msg;

	private Object data;

	public ResponseResult(boolean isSuccess, String msg, Object data) {
		super();
		this.isSuccess = isSuccess;
		this.msg = msg;
		this.data = data;
	}

	public ResponseResult() {
	}

	public static ResponseResult ok(String msg, Object data) {
		return new ResponseResult(true, msg, data);
	}

	public static ResponseResult ok(Object data) {
		return new ResponseResult(true, null, data);
	}
	
	public static ResponseResult ok() {
		return new ResponseResult(true, null, null);
	}

	public static ResponseResult error(String msg) {
		return new ResponseResult(false, msg, null);
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
