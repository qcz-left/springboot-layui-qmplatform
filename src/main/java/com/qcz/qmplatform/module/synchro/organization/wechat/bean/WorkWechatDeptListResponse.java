package com.qcz.qmplatform.module.synchro.organization.wechat.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 企业微信获取部门列表接口响应
 */
@Data
@Accessors(chain = true)
public class WorkWechatDeptListResponse implements Serializable {

    /**
     * 返回码
     */
    private int errcode;

    /**
     * 对返回码的文本描述内容
     */
    private String errmsg;

    /**
     * 部门列表数据
     */
    private List<WorkWechatDeptResult> department;
}
