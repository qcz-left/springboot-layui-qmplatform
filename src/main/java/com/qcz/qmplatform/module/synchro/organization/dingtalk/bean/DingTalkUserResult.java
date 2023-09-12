package com.qcz.qmplatform.module.synchro.organization.dingtalk.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 钉钉-获取用户列表返回的结果
 */
@Data
@Accessors(chain = true)
public class DingTalkUserResult implements Serializable {

    /**
     * 是否还有更多的数据
     */
    private boolean has_more;

    /**
     * 下一次分页的游标
     */
    private int next_cursor;

    /**
     * 用户信息列表
     */
    private List<DingTalkUserResultItem> list;
}
