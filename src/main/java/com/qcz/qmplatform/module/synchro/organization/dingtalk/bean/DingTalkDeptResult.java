package com.qcz.qmplatform.module.synchro.organization.dingtalk.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 钉钉-获取部门列表返回的部门结果
 */
@Data
@Accessors(chain = true)
public class DingTalkDeptResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    private long dept_id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父部门ID
     */
    private long parent_id;
}
