package com.qcz.qmplatform.module.business.system.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 组织架构通用设置参数
 */
@Data
@Accessors(chain = true)
public class OrgCommonConfigDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 未知部门
     */
    private String unknownDept;
}
