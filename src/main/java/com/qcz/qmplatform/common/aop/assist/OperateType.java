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
    INSTANCE(0, null),

    /**
     * 登录
     */
    LOGIN(1, "登录"),

    /**
     * 登出
     */
    LOGOUT(-1, "登出"),

    /**
     * 查询
     */
    QUERY(2, "查询"),

    /**
     * 新增
     */
    INSERT(3, "新增"),

    /**
     * 更新
     */
    UPDATE(4, "更新"),

    /**
     * 删除
     */
    DELETE(5, "删除");

    private final int type;

    private final String name;

}
