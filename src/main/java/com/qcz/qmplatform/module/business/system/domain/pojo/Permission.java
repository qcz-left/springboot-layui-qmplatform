package com.qcz.qmplatform.module.business.system.domain.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 权限
 */
@Data
@Accessors(chain = true)
public class Permission implements Serializable {

    private String permissionId;

    private String permissionName;

    private String icon;

    private String code;

    private Integer iorder;

    private String parentId;

    private Integer permissionType;

    private String linkUrl;

    /**
     * 是否显示：0 不显示；1 显示
     */
    private Integer display;

}
