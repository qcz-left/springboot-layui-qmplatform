package com.qcz.qmplatform.module.business.system.domain.vo;

import com.qcz.qmplatform.module.business.system.domain.UserGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class UserGroupVO extends UserGroup implements Serializable {

    /**
     * 上级用户组名称
     */
    private String parentName;
}
