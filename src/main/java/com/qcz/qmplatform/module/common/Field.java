package com.qcz.qmplatform.module.common;

/**
 * 字段属性和值 实体类
 * @author quchangzhong
 */
public class Field {

	private String cloumn;
	private Object value;
	public String getCloumn() {
		return cloumn;
	}
	public void setCloumn(String cloumn) {
		this.cloumn = cloumn;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}
