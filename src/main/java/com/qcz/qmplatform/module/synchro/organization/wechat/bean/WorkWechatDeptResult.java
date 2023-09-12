package com.qcz.qmplatform.module.synchro.organization.wechat.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 企业微信获取部门结果
 */
@Data
@Accessors(chain = true)
public class WorkWechatDeptResult implements Serializable {

    /**
     * 部门id
     */
    private int id;

    /**
     * 父部门id
     */
    private int parentid;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 在父部门中的次序值。order值大的排序靠前
     */
    private int order;
}
