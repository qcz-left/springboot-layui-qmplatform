package com.qcz.qmplatform.common.bean;

import java.io.Serializable;
import java.util.List;

public class PageResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据长度
     */
    private Long count;

    /**
     * 返回的数据集合
     */
    private List<?> list;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
