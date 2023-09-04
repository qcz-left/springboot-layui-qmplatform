package com.qcz.qmplatform.module.business.system.domain.qo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 用户列表查询参数
 */
@Data
@Accessors(chain = true)
public class UserQO implements Serializable {

    private String username;
    private String userSex;
    private List<String> organizationIds;
    private String organizationIdsStr;
    /**
     * 递归条件下的所有部门id
     */
    private List<String> cascOrganizationIds;

    /**
     * 所属部门是否精确查询
     */
    private int organizationExact;

}
