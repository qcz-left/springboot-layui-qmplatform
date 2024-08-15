package com.qcz.qmplatform.common.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据
 */
@Data
@Accessors(chain = true)
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据长度
     */
    private Long count;

    /**
     * 返回的数据集合
     */
    private List<T> list;

}
