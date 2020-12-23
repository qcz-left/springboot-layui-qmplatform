package com.qcz.qmplatform.common.aop.assist;

/**
 * 操作类型
 *
 * @author changzhongq
 */
public enum OperateType {

    INSTANCE(0), LOGIN(1), LOGOUT(-1), FIND(2), INSERT(3), UPDATE(4), DELETE(5);

    private final int type;

    OperateType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
