package com.qcz.qmplatform.module.business.system.domain.qo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserGroupUserQO implements Serializable {

    /**
     * 用户组ID
     */
    private String userGroupId;

    /**
     * 用户名
     */
    private String username;
}
