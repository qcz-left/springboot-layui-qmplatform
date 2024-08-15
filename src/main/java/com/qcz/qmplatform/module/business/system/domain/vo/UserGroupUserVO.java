package com.qcz.qmplatform.module.business.system.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserGroupUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 部门名
     */
    private String organizationName ;
}
