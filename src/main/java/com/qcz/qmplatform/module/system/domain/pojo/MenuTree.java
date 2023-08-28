package com.qcz.qmplatform.module.system.domain.pojo;

import com.qcz.qmplatform.common.bean.Tree;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 树形菜单数据，这里和按钮数据一起展示成权限数据
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class MenuTree extends Tree {

    /**
     * 图标字符串，一般只有菜单才有图标
     */
    private String icon;

    /**
     * 权限码（权限标识）
     */
    private String code;

    /**
     * 链接路径
     */
    private String linkUrl;

    /**
     * 排序
     */
    private Integer iorder;

    /**
     * 权限类型（1：菜单，2：按钮）
     */
    private Integer permissionType;

    /**
     * 是否显示（1：是，2：否）
     */
    private Integer display;

}
