package com.qcz.qmplatform.common.aop.assist;

/**
 * 操作类型
 * @author changzhongq
 * @time 2018年11月23日 下午4:18:19
 */
public enum OperateType {

	INSTANCE(0), LOGIN(1), LOGOUT(-1), FIND(2), INSERT(3), UPDATE(4), DELETE(5);

	private int type;

	private OperateType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

}
