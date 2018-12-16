package com.qcz.qmplatform.common.message;

import java.io.Serializable;

/**
 * 分页请求参数（layui风格）
 * @author changzhongq
 * @time 2018年6月12日 上午9:56:55
 */
public class PageRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 页数
	 */
	private Integer page;
	
	/**
	 * 每页记录数
	 */
	private Integer limit;
	
	/**
	 * 排序字段
	 */
	private String field;
	
	/**
	 * 排序方式
	 */
	private String order;

	public PageRequest() {

	}

	public PageRequest(Integer page, Integer limit, String field, String order) {
		this.page = page;
		this.limit = limit;
		this.field = field;
		this.order = order;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
}
