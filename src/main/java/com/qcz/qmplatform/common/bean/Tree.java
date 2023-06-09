package com.qcz.qmplatform.common.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class Tree implements Serializable {

    private String id;

    private String name;

    private String parentId;

    private List<? extends Tree> childes;

    private boolean hasParent;

    private boolean hasChild;

    private int level;

}
