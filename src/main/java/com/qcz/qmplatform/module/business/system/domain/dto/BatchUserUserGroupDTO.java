package com.qcz.qmplatform.module.business.system.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class BatchUserUserGroupDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户组ID
     */
    private String userGroupId;

    /**
     * 用户ID集合
     */
    private List<String> userIds;
}
