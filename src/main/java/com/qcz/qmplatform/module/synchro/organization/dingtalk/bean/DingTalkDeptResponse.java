package com.qcz.qmplatform.module.synchro.organization.dingtalk.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 钉钉-获取部门列表返回参数
 */
@Data
@Accessors(chain = true)
public class DingTalkDeptResponse implements Serializable {

    /**
     * 请求ID
     */
    private String request_id;

    /**
     * 返回码
     */
    private int errcode;

    /**
     * 调用失败时返回的错误信息
     */
    private String errmsg;

    /**
     * 部门列表
     */
    private List<DingTalkDeptResult> result;

}
