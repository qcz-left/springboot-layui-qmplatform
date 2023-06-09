package com.qcz.qmplatform.module.system.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RolePermissionVO {

    private String roleId;

    private List<String> permissionIds;

}
