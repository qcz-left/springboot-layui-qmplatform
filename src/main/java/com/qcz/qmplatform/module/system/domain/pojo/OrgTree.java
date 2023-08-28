package com.qcz.qmplatform.module.system.domain.pojo;

import com.qcz.qmplatform.common.bean.Tree;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OrgTree extends Tree {

    /**
     * 权限码（权限标识）
     */
    private String code;

    /**
     * 排序
     */
    private Integer iorder;

    /**
     * 类型（1：部门；2：用户）
     */
    private Integer itype;

}
