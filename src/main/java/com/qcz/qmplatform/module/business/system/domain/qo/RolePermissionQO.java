package com.qcz.qmplatform.module.business.system.domain.qo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RolePermissionQO {

    private String roleId;

    private List<String> permissionIds;

}
