package com.qcz.qmplatform.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 分页请求参数
 *
 * @author quchangzhong
 * @since 2019年6月5日
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
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

}
