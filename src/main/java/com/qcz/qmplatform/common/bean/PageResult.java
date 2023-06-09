package com.qcz.qmplatform.common.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class PageResult implements Serializable {

    /**
     * 数据长度
     */
    private Long count;

    /**
     * 返回的数据集合
     */
    private List<?> list;

}
