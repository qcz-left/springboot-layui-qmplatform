package com.qcz.qmplatform.module.business.system.domain.pojo;

import com.qcz.qmplatform.common.validation.EnumValue;
import com.qcz.qmplatform.common.validation.groups.Update;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 权限
 */
@Data
@Accessors(chain = true)
public class Permission implements Serializable {

    /**
     * 权限ID
     */
    @NotBlank(groups = Update.class)
    private String permissionId;

    /**
     * 权限名称
     */
    @NotBlank
    private String permissionName;

    /**
     * 图标
     */
    private String icon;

    /**
     * 权限码
     */
    @NotBlank
    private String code;

    /**
     * 排序
     */
    private Integer iorder;

    /**
     * 父ID
     */
    private String parentId;

    /**
     * 权限类型（1：菜单；2：按钮）
     */
    @EnumValue(intValues = {1, 2})
    private Integer permissionType;

    /**
     * 链接
     */
    private String linkUrl;

    /**
     * 是否显示：0 不显示；1 显示
     */
    @EnumValue(intValues = {0, 1})
    private Integer display;

}
