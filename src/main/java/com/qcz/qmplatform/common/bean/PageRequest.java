package com.qcz.qmplatform.common.bean;

import java.io.Serializable;

/**
 * @author quchangzhong
 * @since 2019年6月5日
 */
public class PageRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 页数，默认第一页
     */
    private Integer page;

    /**
     * 每页记录数，默认显示10条
     */
    private Integer limit;

    /**
     * 排序字段
     */
    private String orderName;

    /**
     * 排序方式
     */
    private String order;

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

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public PageRequest(Integer page, Integer limit, String orderName, String order) {
        this.page = page;
        this.limit = limit;
        this.orderName = orderName;
        this.order = order;
    }
}
