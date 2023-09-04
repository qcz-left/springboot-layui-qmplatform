package com.qcz.qmplatform.module.business.system.domain.vo;

import com.qcz.qmplatform.module.business.system.domain.pojo.Permission;
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
