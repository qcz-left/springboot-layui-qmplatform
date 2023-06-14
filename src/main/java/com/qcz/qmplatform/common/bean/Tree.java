package com.qcz.qmplatform.common.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 树结构数据
 */
@Data
@Accessors(chain = true)
public class Tree implements Serializable {

    /**
     * 唯一标识，节点ID
     */
    private String id;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 父节点ID
     */
    private String parentId;

    /**
     * 字节点集合
     */
    private List<? extends Tree> childes;

    /**
     * 是否有父节点
     */
    private boolean hasParent;

    /**
     * 是否有子节点
     */
    private boolean hasChild;

    /**
     * 层级（从0开始...）
     */
    private int level;

}
