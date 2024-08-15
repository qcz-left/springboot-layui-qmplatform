package com.qcz.qmplatform.module.business.system.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 移动用户到另一个部门
 */
@Data
public class MoveUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID集合
     */
    private List<String> userIds;

    /**
     * 部门ID
     */
    private String deptId;
}
