package com.qcz.qmplatform.module.business.system.domain.qo;

import com.qcz.qmplatform.module.business.system.domain.Organization;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrganizationQO extends Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户组ID，用于过滤已经存在的用户
     */
    private String notExistsUserGroupId;
}
