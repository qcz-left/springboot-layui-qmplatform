package com.qcz.qmplatform.module.business.system.domain.qo;

import com.qcz.qmplatform.module.business.system.domain.pojo.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class PermissionQO extends Permission {

    private String userId;

    /**
     * select中排除的菜单ID
     */
    private List<String> notExistsMenuIds;

}
