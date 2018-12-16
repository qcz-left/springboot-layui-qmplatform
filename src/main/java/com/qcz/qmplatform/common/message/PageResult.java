package com.qcz.qmplatform.common.message;

import java.util.List;

/**
 * 分页数据
 * @author changzhongq
 * @time 2018年6月11日 下午7:02:57
 */
public class PageResult {

	/**
	 * 接口状态
	 */
	private String code;

	/**
	 * 提示文本
	 */
	private String msg;

	/**
	 * 数据长度
	 */
	private Long count;

	/**
	 * 返回的数据集合
	 */
	private List<?> data;

	public PageResult() {

	}

	public PageResult(List<?> data) {
		this.data = data;
		this.code = "0";
	}

	public PageResult(String code, String msg, Long count, List<?> data) {
		this.code = code;
		this.msg = msg;
		this.count = count;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

}
