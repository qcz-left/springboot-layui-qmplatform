package com.qcz.qmplatform.module.system.vo;

import com.qcz.qmplatform.module.system.pojo.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class PermissionVO extends Permission {

    private String userId;

    /**
     * select中排除的菜单ID
     */
    private List<String> notExistsMenuIds;

}
