package com.qcz.qmplatform.common.aop.assist;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作类型
 *
 * @author changzhongq
 */
@Getter
@AllArgsConstructor
public enum OperateType {

    /**
     * 无实际意义
     */
    INSTANCE(0),

    /**
     * 登录
     */
    LOGIN(1),

    /**
     * 登出
     */
    LOGOUT(-1),

    /**
     * 查询
     */
    QUERY(2),

    /**
     * 新增
     */
    INSERT(3),

    /**
     * 更新
     */
    UPDATE(4),

    /**
     * 删除
     */
    DELETE(5);

    private final int type;

}
